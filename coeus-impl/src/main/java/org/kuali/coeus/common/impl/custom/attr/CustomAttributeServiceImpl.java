/*
 * Copyright 2005-2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.coeus.common.impl.custom.attr;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.kuali.coeus.common.framework.custom.DocumentCustomData;
import org.kuali.coeus.common.framework.custom.attr.CustomAttribute;
import org.kuali.coeus.common.framework.custom.attr.CustomAttributeDataType;
import org.kuali.coeus.common.framework.custom.attr.CustomAttributeDocument;
import org.kuali.coeus.common.framework.custom.attr.CustomAttributeService;
import org.kuali.coeus.common.framework.custom.arg.ArgValueLookup;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.infrastructure.PropertyConstants;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.coreservice.framework.parameter.ParameterService;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.WorkflowDocumentFactory;
import org.kuali.rice.kew.api.document.attribute.WorkflowAttributeDefinition;
import org.kuali.rice.kns.service.BusinessObjectDictionaryService;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.DataDictionaryService;
import org.kuali.rice.krad.data.DataObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * This class provides the implementation of the Custom Attribute Service.
 * It provides service methods related to custom attributes.
 */
@Component("customAttributeService")
public class CustomAttributeServiceImpl implements CustomAttributeService {

    private static final String ARGVALUELOOKUPE_CLASS = ArgValueLookup.class.getName();
    
    private static final String EQUAL_CHAR = "=";
    
    @Autowired
    @Qualifier("dataObjectService")
    private DataObjectService dataObjectService;

    @Autowired
    @Qualifier("businessObjectDictionaryService")
	private BusinessObjectDictionaryService businessDictionaryService;
    
    @Autowired
    @Qualifier("dataDictionaryService")
    private DataDictionaryService dataDictionaryService;

    @Autowired
    @Qualifier("businessObjectService")
	private BusinessObjectService businessObjectService;
    
    @Autowired
    @Qualifier("parameterService")
    private ParameterService parameterService;

	@Override
    public Map<String, CustomAttributeDocument> getDefaultCustomAttributeDocuments(String documentTypeCode, List<? extends DocumentCustomData> customDataList) {
        Map<String, CustomAttributeDocument> customAttributeDocuments = new HashMap<String, CustomAttributeDocument>();
        Map<String, String> queryMap = new HashMap<String, String>();
        queryMap.put(PropertyConstants.DOCUMENT.TYPE_NAME.toString(), documentTypeCode);
        
        List<CustomAttributeDocument> customAttributeDocumentList = 
            (List<CustomAttributeDocument>) businessObjectService.findMatching(CustomAttributeDocument.class, queryMap);

        HashSet<Long> customIds = new HashSet<Long>();
        if (customDataList != null) {
            customIds = getCurrentCustomAttributeIds(customDataList);
        }
        for(CustomAttributeDocument customAttributeDocument:customAttributeDocumentList) {
            boolean customAttributeExists = false;
            if (!customIds.isEmpty() && customIds.contains(customAttributeDocument.getId().longValue())) {
                customAttributeExists = true;
            }

            if (customAttributeDocument.isActive() || customAttributeExists) {
                customAttributeDocuments.put(customAttributeDocument.getId().toString(), customAttributeDocument);
            }
        }
        return customAttributeDocuments;
    }
    
    protected HashSet<Long> getCurrentCustomAttributeIds(List<? extends DocumentCustomData> customDataList) {
        HashSet<Long> customIds = new HashSet<Long>();
        for(DocumentCustomData customData : customDataList) {
            customIds.add(customData.getCustomAttributeId());
        }
        return customIds;
    }

    @Override
    public void setCustomAttributeKeyValue(String documentNumber, Map<String, CustomAttributeDocument> customAttributeDocuments, String attributeName, String networkId) {
        WorkflowDocument workflowDocument = WorkflowDocumentFactory.loadDocument(networkId, documentNumber);
        
        // Not sure to delete all the content, but there is no other options
        workflowDocument.clearAttributeContent();  
        WorkflowAttributeDefinition customDataDef = WorkflowAttributeDefinition.Builder.create(attributeName).build();
        WorkflowAttributeDefinition.Builder refToUpdate = WorkflowAttributeDefinition.Builder.create(customDataDef);
        
        if (customAttributeDocuments != null) {
            for (Map.Entry<String, CustomAttributeDocument> customAttributeDocumentEntry:customAttributeDocuments.entrySet()) {
                CustomAttributeDocument customAttributeDocument = customAttributeDocumentEntry.getValue();
                if (StringUtils.isNotBlank(customAttributeDocument.getCustomAttribute().getValue())) {
                    refToUpdate.addPropertyDefinition(customAttributeDocument.getCustomAttribute().getName(), StringEscapeUtils.escapeXml(customAttributeDocument.getCustomAttribute().getValue()));
                }
            }
        }
        workflowDocument.addAttributeDefinition(refToUpdate.build());
        workflowDocument.saveDocumentData();    
    }

    @Override
    public CustomAttributeDataType getCustomAttributeDataType(String dataTypeCode) {

        if (StringUtils.isNotEmpty(dataTypeCode)) {
            return (CustomAttributeDataType) dataObjectService.findUnique(CustomAttributeDataType.class,
            		QueryByCriteria.Builder.forAttribute("code", dataTypeCode).build());
        }
        return null;
        
    }

    @Override
    public List getLookupReturns(String lookupClass) throws Exception {
        List<String> lookupReturns = new ArrayList<String>();
        if (ARGVALUELOOKUPE_CLASS.equals(lookupClass)) {
            for (ArgValueLookup argValueLookup : businessObjectService.findAll(ArgValueLookup.class)) {
                if (!lookupReturns.contains(argValueLookup.getArgumentName())) {
                    lookupReturns.add(argValueLookup.getArgumentName());
                }
            }
            Collections.sort(lookupReturns);
        }
        else {
            lookupReturns = businessDictionaryService.getLookupFieldNames(Class.forName(lookupClass));
        }
        return lookupReturns;
    }
    
    @Override
    public String getLookupReturnsForAjaxCall(String lookupClass) throws Exception {
        List lookupFieldNames = getLookupReturns(lookupClass);
        String attributeNames="";
        for (Object attributeName : lookupFieldNames) {
            attributeNames += "," + attributeName +";"+ (ARGVALUELOOKUPE_CLASS.equals(lookupClass) ? attributeName : dataDictionaryService.getAttributeLabel(lookupClass,attributeName.toString()));
        }
        return attributeNames;
    }

    /**
     * Accessor for <code>{@link DataObjectService}</code>
     *
     * @param dataObjectService DataObjectService
     */
    public void setDataObjectService(DataObjectService dataObjectService) {
        this.dataObjectService = dataObjectService;
    }

    /**
     * Accessor for <code>{@link DataObjectService}</code>
     *
     * @return DataObjectService
     */
    public DataObjectService getDataObjectService() {
        return dataObjectService;
    }

    public DataDictionaryService getDataDictionaryService() {
		return dataDictionaryService;
	}

	public void setDataDictionaryService(DataDictionaryService dataDictionaryService) {
		this.dataDictionaryService = dataDictionaryService;
	}

	public BusinessObjectDictionaryService getBusinessDictionaryService() {
		return businessDictionaryService;
	}

	public void setBusinessDictionaryService(
			BusinessObjectDictionaryService businessDictionaryService) {
		this.businessDictionaryService = businessDictionaryService;
	}
	
	public BusinessObjectService getBusinessObjectService() {
		return businessObjectService;
	}

	public void setBusinessObjectService(BusinessObjectService businessObjectService) {
		this.businessObjectService = businessObjectService;
	}

	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public boolean isRequired(String dataTypeCode, CustomAttribute attr, List<? extends DocumentCustomData> customDataList)
	{
		Map<String, CustomAttributeDocument> map = getDefaultCustomAttributeDocuments(dataTypeCode, customDataList );
		for(Map.Entry<String, CustomAttributeDocument> document: map.entrySet()) {
			if( document.getValue()!=null && document.getValue().getCustomAttribute()!=null && ObjectUtils.equals(document.getValue().getCustomAttribute().getId(),attr.getId()))
				return document.getValue().isRequired();
		}
		return false;
	}
	
	public Map<String, String> getDocumentTypeMap() {
		Map<String, String> documentTypes = new HashMap<String, String>();
        for (String documentType : getDocumentTypeParam()) {
            String[] params = documentType.split(EQUAL_CHAR);
            documentTypes.put(params[0], params[1]);
        }
        return documentTypes;
	}
	
	public Map<String, String> getReverseDocumentTypeMap() {
		Map<String, String> documentTypes = new HashMap<String, String>();
        for (String documentType : getDocumentTypeParam()) {
            String[] params = documentType.split(EQUAL_CHAR);
            documentTypes.put(params[1].replace(" ", "+"), params[0]);
        }
        return documentTypes;
	}
	
	protected Collection<String> getDocumentTypeParam() {
		return getParameterService().getParameterValuesAsString(Constants.KC_GENERIC_PARAMETER_NAMESPACE,
                Constants.CUSTOM_ATTRIBUTE_DOCUMENT_DETAIL_TYPE_CODE, Constants.CUSTOM_ATTRIBUTE_DOCUMENT_PARAM_NAME);
	}
}
