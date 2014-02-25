/*
 * Copyright 2005-2013 The Kuali Foundation.
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
package org.kuali.kra.s2s.generator.impl;

import gov.grants.apply.forms.edAbstractV10.AbstractAttachmentsDocument;
import gov.grants.apply.forms.edAbstractV10.AbstractAttachmentsDocument.AbstractAttachments;
import gov.grants.apply.system.attachmentsV10.AttachedFileDataType;
import org.apache.xmlbeans.XmlObject;
import org.kuali.kra.proposaldevelopment.bo.Narrative;
import org.kuali.kra.proposaldevelopment.document.ProposalDevelopmentDocument;
import org.kuali.kra.s2s.util.S2SConstants;

/**
 * 
 * This class is used to generate XML Document object for grants.gov EDAbstractV1.0. This form is generated using XMLBean API's
 * generated by compiling EDAbstractV1.0 schema.
 * 
 * @author Kuali Research Administration Team (kualidev@oncourse.iu.edu)
 */
public class EDAbstractV1_0Generator extends EDAbstractBaseGenerator {


    /**
     * 
     * This method returns AbstractAttachmentsDocument object based on proposal development document which contains the
     * AbstractAttachmentsDocument informations about abstract type attachment for a particular proposal
     * 
     * @return abstractDocument(AbstractAttachmentsDocument) {@link XmlObject} of type AbstractAttachmentsDocument.
     */
    private AbstractAttachmentsDocument getAbstractAttachments() {

        AbstractAttachmentsDocument abstractDocument = AbstractAttachmentsDocument.Factory.newInstance();
        AbstractAttachments abstractAttachments = AbstractAttachments.Factory.newInstance();
        abstractAttachments.setFormVersion(S2SConstants.FORMVERSION_1_0);
        abstractAttachments.setAttachments(AttachedFileDataType.Factory.newInstance());
        AttachedFileDataType attachedFileDataType = null;
        for (Narrative narrative : pdDoc.getDevelopmentProposal().getNarratives()) {
            if (narrative.getNarrativeTypeCode() != null && narrative.getNarrativeTypeCode().equals(ABSTRACT_ATTACHMENT_CODE)) {
            	attachedFileDataType = getAttachedFileType(narrative);
            	if(attachedFileDataType != null){
            		abstractAttachments.setAttachments(attachedFileDataType);
            		break;
            	}
            }
        }
        abstractDocument.setAbstractAttachments(abstractAttachments);
        return abstractDocument;
    }

    /**
     * This method creates {@link XmlObject} of type {@link AbstractAttachmentsDocument} by populating data from the given
     * {@link ProposalDevelopmentDocument}
     * 
     * @param proposalDevelopmentDocument for which the {@link XmlObject} needs to be created
     * @return {@link XmlObject} which is generated using the given {@link ProposalDevelopmentDocument}
     * @see org.kuali.kra.s2s.generator.S2SFormGenerator#getFormObject(ProposalDevelopmentDocument)
     */
    public XmlObject getFormObject(ProposalDevelopmentDocument proposalDevelopmentDocument) {

        this.pdDoc = proposalDevelopmentDocument;
        return getAbstractAttachments();
    }

    /**
     * This method typecasts the given {@link XmlObject} to the required generator type and returns back the document of that
     * generator type.
     * 
     * @param xmlObject which needs to be converted to the document type of the required generator
     * @return {@link XmlObject} document of the required generator type
     * @see org.kuali.kra.s2s.generator.S2SFormGenerator#getFormObject(XmlObject)
     */
    public XmlObject getFormObject(XmlObject xmlObject) {

        AbstractAttachmentsDocument abstractDocument = AbstractAttachmentsDocument.Factory.newInstance();
        AbstractAttachments abstractAttachments = (AbstractAttachments) xmlObject;
        abstractDocument.setAbstractAttachments(abstractAttachments);
        return abstractDocument;
    }
}
