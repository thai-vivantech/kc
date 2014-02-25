/*
 * Copyright 2005-2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
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
package org.kuali.kra.iacuc.specialreview;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.coeus.common.specialreview.impl.rule.event.AddSpecialReviewEvent;
import org.kuali.kra.iacuc.IacucProtocolAction;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.infrastructure.KeyConstants;
import org.kuali.kra.protocol.ProtocolDocumentBase;
import org.kuali.kra.protocol.ProtocolFormBase;
import org.kuali.kra.protocol.specialreview.ProtocolSpecialReviewBase;
import org.kuali.rice.krad.util.KRADConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * This class represents the Struts Action for Special Review page(ProtocolSpecialReviewBase.jsp).
 */
public class IacucProtocolSpecialReviewAction extends IacucProtocolAction {
    
    private static final String CONFIRM_DELETE_SPECIAL_REVIEW_KEY = "confirmDeleteSpecialReview";
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = super.execute(mapping, form, request, response);
        
        ProtocolFormBase protocolForm = (ProtocolFormBase) form;
        
        protocolForm.getSpecialReviewHelper().prepareView();
        
        return forward;
    }

    /**
     * Adds a Protocol Special Review to the list.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward addSpecialReview(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ProtocolFormBase protocolForm = (ProtocolFormBase) form;
        ProtocolDocumentBase document = protocolForm.getProtocolDocument();
        ProtocolSpecialReviewBase specialReview = protocolForm.getSpecialReviewHelper().getNewSpecialReview();
        List<ProtocolSpecialReviewBase> specialReviews = document.getProtocol().getSpecialReviews();
        
        if (applyRules(new AddSpecialReviewEvent<ProtocolSpecialReviewBase>(document, specialReview, specialReviews, false))) {
            specialReview.setSpecialReviewNumber(document.getDocumentNextValue(Constants.SPECIAL_REVIEW_NUMBER));
            document.getProtocol().getSpecialReviews().add(specialReview);
            protocolForm.getSpecialReviewHelper().setNewSpecialReview(new IacucProtocolSpecialReview());
        }
        
        return mapping.findForward(Constants.MAPPING_BASIC);
    }
    
    /**
     * Deletes a special review item after confirmation.
     * 
     * @param mapping the action mapping
     * @param form the action form
     * @param request the request
     * @param response the response
     * @return the action forward
     * @throws Exception if unable to delete the special review
     */
    public ActionForward deleteSpecialReview(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
        throws Exception {

        return confirm(buildParameterizedConfirmationQuestion(mapping, form, request, response, CONFIRM_DELETE_SPECIAL_REVIEW_KEY,
                KeyConstants.QUESTION_SPECIAL_REVIEW_DELETE_CONFIRMATION), CONFIRM_DELETE_SPECIAL_REVIEW_KEY, "");
    }
    
    /**
     * Deletes a special review item only if the user confirms it.
     * 
     * @param mapping the action mapping
     * @param form the action form
     * @param request the request
     * @param response the response
     * @return the action forward
     * @throws Exception if unable to delete the special review
     */
    public ActionForward confirmDeleteSpecialReview(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
        throws Exception {
        
        Object question = request.getParameter(KRADConstants.QUESTION_INST_ATTRIBUTE_NAME);
        if (CONFIRM_DELETE_SPECIAL_REVIEW_KEY.equals(question)) {
            ProtocolFormBase protocolForm = (ProtocolFormBase) form;
            ProtocolDocumentBase document = protocolForm.getProtocolDocument();
            
            document.getProtocol().getSpecialReviews().remove(getLineToDelete(request));
        }
        
        return mapping.findForward(Constants.MAPPING_BASIC);
    }

    
}
