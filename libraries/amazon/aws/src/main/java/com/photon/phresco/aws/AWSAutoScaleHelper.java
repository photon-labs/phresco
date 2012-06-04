package com.photon.phresco.aws;

import java.util.ArrayList;
import java.util.Collection;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.autoscaling.AmazonAutoScaling;
import com.amazonaws.services.autoscaling.AmazonAutoScalingClient;
import com.amazonaws.services.autoscaling.model.CreateAutoScalingGroupRequest;
import com.amazonaws.services.autoscaling.model.CreateLaunchConfigurationRequest;
import com.amazonaws.services.autoscaling.model.PutScalingPolicyRequest;
import com.amazonaws.services.autoscaling.model.PutScalingPolicyResult;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.model.PutMetricAlarmRequest;

public class AWSAutoScaleHelper {
	public String accessKey;
	public String secretKey;
	public String autoScaleName;
	public String instanceType;
	public String imageId;
	public String securityGroup;
	public String keyPair;
	public Collection<String> availabilityZones;
	public String userData;
	public int minSize;
	public int maxSize;
	public int desiredSize;
	public String loadBalancer;
	public String scaleUpStatistic;
	public String scaleUpMetric;
	public String scaleUpNamespace;
	public int scaleUpEvalPeriods;
	public int scaleUpPeriod;
	public String scaleUpComparison;
	public double scaleUpThreshold;
	public String scaleUpUnit;
	public String scaleUpAdjustmentType;
	public int scaleUpAdjustment;
	public int scaleUpCooldown;
	public String scaleDownStatistic;
	public String scaleDownMetric;
	public String scaleDownNamespace;
	public int scaleDownEvalPeriods;
	public int scaleDownPeriod;
	public String scaleDownComparison;
	public double scaleDownThreshold;
	public String scaleDownUnit;
	public String scaleDownAdjustmentType;
	public int scaleDownAdjustment;
	public int scaleDownCooldown;

	public void setupAutoScaling() {
		// TODO validate that all necessary member variables have been set
		if(desiredSize <= minSize || desiredSize >= maxSize)
			desiredSize = minSize;

		AWSCredentials creds = new BasicAWSCredentials(accessKey, secretKey);

		try {
			AmazonAutoScaling autoScaling = new AmazonAutoScalingClient(creds);

			//Launch configuration
			String launchConfigName = "Phresco-LC-" + autoScaleName;
			CreateLaunchConfigurationRequest launchConfigRequest = new CreateLaunchConfigurationRequest()
					.withLaunchConfigurationName(launchConfigName)
					.withInstanceType(instanceType).withImageId(imageId)
					.withKeyName(keyPair).withSecurityGroups(securityGroup)
					.withUserData(userData);
			autoScaling.createLaunchConfiguration(launchConfigRequest);

			//Auto scaling group
			String autoScalingGroupName = "Phresco-ASG-" + autoScaleName;
			CreateAutoScalingGroupRequest autoScalingGroupRequest = new CreateAutoScalingGroupRequest()
					.withAutoScalingGroupName(autoScalingGroupName)
					.withAvailabilityZones(availabilityZones)
					.withLaunchConfigurationName(launchConfigName)
					.withMinSize(minSize)
					.withMaxSize(maxSize)
					.withDesiredCapacity(desiredSize);
			if (!isBlank(loadBalancer)) {
				ArrayList<String> lbs = new ArrayList<String>();
				lbs.add(loadBalancer);
				autoScalingGroupRequest.setLoadBalancerNames(lbs);
			}
			autoScaling.createAutoScalingGroup(autoScalingGroupRequest);

			//Scale up policy
			String scaleUpPolicyName = "Phresco-Pol-Up-" + autoScaleName;
			PutScalingPolicyRequest scaleUpPolicyRequest = new PutScalingPolicyRequest()
				.withPolicyName(scaleUpPolicyName)
				.withAutoScalingGroupName(autoScalingGroupName)
				.withScalingAdjustment(scaleUpAdjustment)
				.withAdjustmentType(scaleUpAdjustmentType)
				.withCooldown(scaleUpCooldown);
			PutScalingPolicyResult scaleUpPolicyResult = autoScaling.putScalingPolicy(scaleUpPolicyRequest);

			AmazonCloudWatch cloudWatch = new AmazonCloudWatchClient(creds);
			
			//Scale up alarm
			String upAlarmName = "Phresco-Pol-UpAlarm-" + autoScaleName; 
			PutMetricAlarmRequest upAlarmRequest = new PutMetricAlarmRequest()
				.withAlarmName(upAlarmName)
				.withEvaluationPeriods(scaleUpEvalPeriods)
				.withPeriod(scaleUpPeriod)
				.withComparisonOperator(scaleUpComparison)
				.withMetricName(scaleUpMetric)
				.withNamespace(scaleUpNamespace)
				.withStatistic(scaleUpStatistic)
				.withThreshold(scaleUpThreshold)
				.withUnit(scaleUpUnit)
				.withAlarmActions(scaleUpPolicyResult.getPolicyARN());
			cloudWatch.putMetricAlarm(upAlarmRequest);
			
			//Scale down policy
			String scaleDownPolicyName = "Phresco-Pol-Dn-" + autoScaleName;
			PutScalingPolicyRequest scaleDownPolicyRequest = new PutScalingPolicyRequest()
				.withPolicyName(scaleDownPolicyName)
				.withAutoScalingGroupName(autoScalingGroupName)
				.withScalingAdjustment(scaleDownAdjustment)
				.withAdjustmentType(scaleDownAdjustmentType)
				.withCooldown(scaleDownCooldown);
			PutScalingPolicyResult scaleDownPolicyResult = autoScaling.putScalingPolicy(scaleDownPolicyRequest);
			
			//Scale down alarm
			String downAlarmName = "Phresco-Pol-DnAlarm-" + autoScaleName; 
			PutMetricAlarmRequest downAlarmRequest = new PutMetricAlarmRequest()
				.withAlarmName(downAlarmName)
				.withEvaluationPeriods(scaleDownEvalPeriods)
				.withPeriod(scaleDownPeriod)
				.withComparisonOperator(scaleDownComparison)
				.withMetricName(scaleDownMetric)
				.withNamespace(scaleDownNamespace)
				.withStatistic(scaleDownStatistic)
				.withThreshold(scaleDownThreshold)
				.withUnit(scaleDownUnit)
				.withAlarmActions(scaleDownPolicyResult.getPolicyARN());
			cloudWatch.putMetricAlarm(downAlarmRequest);
			
		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			ace.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	private static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}
}
