/*
 * Copyright 2005-2014 The Kuali Foundation.
 * 
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.coeus.s2sgen.impl.generate.support;

import gov.grants.apply.forms.faithBasedSurveyOnEEOV12.SurveyOnEEODocument;
import gov.grants.apply.forms.faithBasedSurveyOnEEOV12.SurveyOnEEODocument.SurveyOnEEO;
import org.apache.xmlbeans.XmlObject;
import org.kuali.coeus.common.api.org.OrganizationContract;
import org.kuali.coeus.propdev.api.s2s.S2sOpportunityContract;
import org.kuali.coeus.s2sgen.impl.generate.FormGenerator;
import org.kuali.coeus.s2sgen.impl.generate.FormVersion;
import org.kuali.coeus.s2sgen.impl.generate.S2SBaseFormGenerator;
import org.kuali.coeus.propdev.api.core.ProposalDevelopmentDocumentContract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;


/**
 * 
 * This class is used to generate XML Document object for grants.gov FaithBasedSurveyOnEEOV1.2. This form is generated using XMLBean
 * API's generated by compiling FaithBasedSurveyOnEEOV1.2 schema.
 * 
 * @author Kuali Research Administration Team (kualidev@oncourse.iu.edu)
 */
@FormGenerator("FaithBasedSurveyOnEEOV1_2Generator")
public class FaithBasedSurveyOnEEOV1_2Generator extends S2SBaseFormGenerator {

    @Value("http://apply.grants.gov/forms/FaithBased_SurveyOnEEO-V1.2")
    private String namespace;

    @Value("FaithBased_SurveyOnEEO-V1.2")
    private String formName;

    @Value("classpath:org/kuali/coeus/s2sgen/impl/generate/support/FaithBased_SurveyOnEEO-V1.2.fo.xsl")
    private Resource stylesheet;

    @Value("gov.grants.apply.forms.faithBasedSurveyOnEEOV12")
    private String packageName;

    @Value(DEFAULT_SORT_INDEX)
    private int sortIndex;

    /**
     * 
     * This method returns SurveyOnEEODocument object based on proposal development document which contains the SurveyOnEEODocument
     * informations OrganizationName,DUNSID,OpportunityTitle,CFDANumber for a particular proposal
     * 
     * @return surveyOnEEODocument(SurveyOnEEODocument) {@link XmlObject} of type SurveyOnEEODocument.
     */
    private SurveyOnEEODocument getSurveyOnEEO() {

        SurveyOnEEODocument surveyOnEEODocument = SurveyOnEEODocument.Factory.newInstance();
        SurveyOnEEO surveyOnEEO = SurveyOnEEO.Factory.newInstance();
        surveyOnEEO.setFormVersion(FormVersion.v1_2.getVersion());

        OrganizationContract organization = null;
        if (pdDoc.getDevelopmentProposal().getApplicantOrganization() != null) {
            organization = pdDoc.getDevelopmentProposal().getApplicantOrganization().getOrganization();
        }

        if (organization != null) {
            if (organization.getOrganizationName() != null) {
                if (organization.getOrganizationName().length() > ORGANIZATON_NAME_MAX_LENGTH) {
                    surveyOnEEO.setOrganizationName(organization.getOrganizationName().substring(0, ORGANIZATON_NAME_MAX_LENGTH));
                }
                else {
                    surveyOnEEO.setOrganizationName(organization.getOrganizationName());
                }
            }
            if (organization.getDunsNumber() != null) {
                if (organization.getDunsNumber().length() > DUNS_NUMBER_MAX_LENGTH) {
                    surveyOnEEO.setDUNSID(organization.getDunsNumber().substring(0, DUNS_NUMBER_MAX_LENGTH));
                }
                else {
                    surveyOnEEO.setDUNSID(organization.getDunsNumber());
                }
            }
        }
        S2sOpportunityContract s2sOpportunity = pdDoc.getDevelopmentProposal().getS2sOpportunity();
        String opportunityTitle = "";
        if (s2sOpportunity != null) {
            opportunityTitle = s2sOpportunity.getOpportunityTitle();
            surveyOnEEO.setCFDANumber(s2sOpportunity.getCfdaNumber());
        }
        surveyOnEEO.setOpportunityTitle(opportunityTitle);
        surveyOnEEODocument.setSurveyOnEEO(surveyOnEEO);
        return surveyOnEEODocument;
    }

    /**
     * This method creates {@link XmlObject} of type {@link SurveyOnEEODocument} by populating data from the given
     * {@link ProposalDevelopmentDocumentContract}
     * 
     * @param proposalDevelopmentDocument for which the {@link XmlObject} needs to be created
     * @return {@link XmlObject} which is generated using the given {@link ProposalDevelopmentDocumentContract}
     */
    public XmlObject getFormObject(ProposalDevelopmentDocumentContract proposalDevelopmentDocument) {

        this.pdDoc = proposalDevelopmentDocument;
        return getSurveyOnEEO();
    }

    @Override
    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @Override
    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    @Override
    public Resource getStylesheet() {
        return stylesheet;
    }

    public void setStylesheet(Resource stylesheet) {
        this.stylesheet = stylesheet;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }
}