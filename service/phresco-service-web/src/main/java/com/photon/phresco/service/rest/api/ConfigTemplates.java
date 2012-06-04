package com.photon.phresco.service.rest.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import com.photon.phresco.exception.PhrescoException;
import com.photon.phresco.model.I18NString;
import com.photon.phresco.model.L10NString;
import com.photon.phresco.model.PropertyTemplate;
import com.photon.phresco.model.SettingsTemplate;
import com.photon.phresco.service.model.ServerConstants;
import com.photon.phresco.util.SizeConstants;

@Path(ServerConstants.REST_API_CONFIG_TEMPLATES)
public class ConfigTemplates implements ServerConstants {
private static final Logger S_LOGGER = Logger.getLogger(Settings.class);
private static Map<String, SettingsTemplate> infoMap = new HashMap<String, SettingsTemplate>();

	static {
		createSettingsTemplates();
	}
	/**
     * Create SettingsTemplates
     * @return
     */
	private static void createSettingsTemplates() {
		// TODO Auto-generated method stub
		List <PropertyTemplate> propertyTemplate = createPropertyTemplate ();
		SettingsTemplate settingsTemplate = new SettingsTemplate ();
		settingsTemplate.setProperties(propertyTemplate);
		infoMap.put(settingsTemplate.getType(), settingsTemplate);
	}

	/**
     * Returns the list of PropertyTemplate
     * @return PropertyTemplate
     * @throws PhrescoException
     */
	private static List<PropertyTemplate> createPropertyTemplate() {
		// TODO Auto-generated method stub
		PropertyTemplate propertyTemplate = new PropertyTemplate ();
		List <PropertyTemplate> propertyTemplateList = new ArrayList<PropertyTemplate> ();
		propertyTemplate.setId(1);
		I18NString intlStr = createIntlStr ();
		propertyTemplate.setDescription(intlStr);
		propertyTemplate.setiDesc("tech-html5-mobile-widget");
		propertyTemplate.setIName("us-en");
		propertyTemplate.setIsRequired(true);
		propertyTemplate.setKey("server.protocol");
		propertyTemplate.setName(intlStr);
		propertyTemplate.setProjectSpecific(true);
		List <String> possibleValues = createPropertyPossibleValues ();
		propertyTemplate.setPossibleValues(possibleValues);
		propertyTemplate.setType("server.protocol");
		propertyTemplateList.add(propertyTemplate);
		return propertyTemplateList;
	}

	/**
     * Returns the list of Possible Values for PropertyTemplate
     * @return String
     */
	private static List<String> createPropertyPossibleValues() {
		// TODO Auto-generated method stub
		List <String> possibleValues = new ArrayList <String> ();
		String [] possibleValuesArray =  new String[] {"java" , "html5"};
		possibleValues.add(possibleValuesArray[0]);
		possibleValues.add(possibleValuesArray[1]);
		return possibleValues;
	}

	/**
     * Returns the list of I18NString
     * @return I18NString
     * @throws PhrescoException
     */
	private static I18NString createIntlStr() {
		// TODO Auto-generated method stub
		Map<String, L10NString> valuesMap = new HashMap<String, L10NString>(SizeConstants.SIZE_VALUES_MAP);
		I18NString intlStr = new I18NString ();
		L10NString l1Str = new L10NString ();
		l1Str.setLang("en");
		l1Str.setValue("US_eng");
		valuesMap.put("US-en", l1Str);
		intlStr.setValuesMap(valuesMap);
		return intlStr;
	}

	/**
	 * Returns the list of SettingsTemplate
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<SettingsTemplate> list(@Context HttpServletRequest request,
			@QueryParam(REST_API_QUERY_PARAM_LIMIT) int limit,
			@QueryParam(REST_API_QUERY_PARAM_OFFSET) int offset) throws PhrescoException {

		HttpSession session = request.getSession(true);
        Object foo = session.getAttribute(REST_API_QUERY_PARAM_SESSION_ID);
		//TODO: JEB -
		//Validate AuthToken
        //Validate Permissions
		return new ArrayList<SettingsTemplate>(infoMap.values());
	}

	/**
	 * Creates the SettingsTemplate objects as specified in the parameter
	 * @param ConfigTemplates
	 * @throws PhrescoException
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(List<SettingsTemplate> settingsTemplates) throws PhrescoException {
        for (SettingsTemplate settingsTemplate : settingsTemplates) {
            String id = settingsTemplate.getType();
            if (infoMap.containsKey(id)) {
                throw new PhrescoException(id + " already exist ");
            }
        }
        for (SettingsTemplate settingsTemplate : settingsTemplates) {
            infoMap.put(settingsTemplate.getType(), settingsTemplate);
        }
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<SettingsTemplate> update(List<SettingsTemplate> settingsTemplates) throws PhrescoException {
		for (SettingsTemplate settingsTemplate : settingsTemplates) {
			String id = settingsTemplate.getType();
			if (!infoMap.containsKey(id)) {
				throw new PhrescoException(id + " does not exist ");
			}
		}
		for (SettingsTemplate settingsTemplate : settingsTemplates) {
			infoMap.put(settingsTemplate.getType(), settingsTemplate);
		}
		return settingsTemplates;
	}

	/**
	 * Deletes all the ConfigTemplates in the Database
	 */
	@DELETE
	public void delete() {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}

	/**
	 * Returns the list of ConfigTemplates
	 * @return
	 * @throws PhrescoException
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public SettingsTemplate get(@PathParam(REST_API_PATH_PARAM_ID) String id) throws PhrescoException {
		SettingsTemplate settingsTemplate = infoMap.get(id);
		if (settingsTemplate == null) {
			throw new PhrescoException(id + " does not exist ");
		}
		return settingsTemplate;
	}

	/**
	 * Creates the ConfigTemplate objects as specified in the parameter
	 * @param ConfigTemplates
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public void create(@PathParam(REST_API_PATH_PARAM_ID) String id,
			SettingsTemplate settingsTemplate) {
		throw new UnsupportedOperationException(ERROR_MSG_UNSUPPORTED_OPERATION);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path(REST_API_PATH_ID)
	public SettingsTemplate update(@PathParam(REST_API_PATH_PARAM_ID) String id, SettingsTemplate
			settingsTemplate) throws PhrescoException {
		if (!id.equals(settingsTemplate.getType())) {
			throw new PhrescoException("The ids does not match");
		}
		if (infoMap.containsKey(id)) {
			throw new PhrescoException(id + " is invalid");
		}
		infoMap.put(id, settingsTemplate);
		return settingsTemplate;
	}

	/**
	 * Deletes the ConfigTemplates as specified by the id
	 * @throws PhrescoException
	 */
	@DELETE
	@Path(REST_API_PATH_ID)
	public void delete(@PathParam(REST_API_PATH_PARAM_ID) String id) throws PhrescoException {
	    if (infoMap.containsKey(id)) {
            throw new PhrescoException(id + " does not exist");
        }

        infoMap.remove(id);
	}

}
