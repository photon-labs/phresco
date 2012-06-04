import java.util.ArrayList;
import java.util.Properties;

import com.photon.phresco.aws.AWSAutoScaleHelper;


public class AutoScaleSample {
	public static void main(String[] args) {
		Properties props = PropertyLoader.loadProperties("AutoScaleSample");
		AWSAutoScaleHelper autoScale = new AWSAutoScaleHelper();
		
		autoScale.accessKey = props.getProperty("accessKey");
		autoScale.secretKey = props.getProperty("secretKey");
		autoScale.autoScaleName = props.getProperty("autoScaleName");
		autoScale.instanceType = props.getProperty("instanceType");
		autoScale.imageId = props.getProperty("imageId");
		autoScale.securityGroup = props.getProperty("securityGroup");
		autoScale.keyPair = props.getProperty("keyPair");
		autoScale.availabilityZones = new ArrayList<String>();
		String[] zones = props.getProperty("availabilityZones").split(",");
		for(String zone : zones)
			autoScale.availabilityZones.add(zone); 
		//autoScale.userData = props.getProperty("userData"); //TODO research this field reg. base64 encoding
		autoScale.minSize = Integer.parseInt(props.getProperty("minSize"));
		autoScale.maxSize = Integer.parseInt(props.getProperty("maxSize"));
		//autoScale.desiredSize = props.getProperty("desiredSize");
		//autoScale.loadBalancer = props.getProperty("loadBalancer");
		autoScale.scaleUpStatistic = props.getProperty("scaleUpStatistic");
		autoScale.scaleUpMetric = props.getProperty("scaleUpMetric");
		autoScale.scaleUpNamespace = props.getProperty("scaleUpNamespace");
		autoScale.scaleUpEvalPeriods = Integer.parseInt(props.getProperty("scaleUpEvalPeriods"));
		autoScale.scaleUpPeriod = Integer.parseInt(props.getProperty("scaleUpPeriod"));
		autoScale.scaleUpComparison = props.getProperty("scaleUpComparison");
		autoScale.scaleUpThreshold = Double.parseDouble(props.getProperty("scaleUpThreshold"));
		autoScale.scaleUpUnit = props.getProperty("scaleUpUnit");
		autoScale.scaleUpAdjustmentType = props.getProperty("scaleUpAdjustmentType");
		autoScale.scaleUpAdjustment = Integer.parseInt(props.getProperty("scaleUpAdjustment"));
		autoScale.scaleUpCooldown = Integer.parseInt(props.getProperty("scaleUpCooldown"));
		autoScale.scaleDownStatistic = props.getProperty("scaleDownStatistic");
		autoScale.scaleDownMetric = props.getProperty("scaleDownMetric");
		autoScale.scaleDownNamespace = props.getProperty("scaleDownNamespace");
		autoScale.scaleDownEvalPeriods = Integer.parseInt(props.getProperty("scaleDownEvalPeriods"));
		autoScale.scaleDownPeriod = Integer.parseInt(props.getProperty("scaleDownPeriod"));
		autoScale.scaleDownComparison = props.getProperty("scaleDownComparison");
		autoScale.scaleDownThreshold = Double.parseDouble(props.getProperty("scaleDownThreshold"));
		autoScale.scaleDownUnit = props.getProperty("scaleDownUnit");
		autoScale.scaleDownAdjustmentType = props.getProperty("scaleDownAdjustmentType");
		autoScale.scaleDownAdjustment = Integer.parseInt(props.getProperty("scaleDownAdjustment"));
		autoScale.scaleDownCooldown = Integer.parseInt(props.getProperty("scaleDownCooldown"));
		
		autoScale.setupAutoScaling();
		System.out.println("Successfully set up auto scaling [" + autoScale.autoScaleName + "].");
	}
}
