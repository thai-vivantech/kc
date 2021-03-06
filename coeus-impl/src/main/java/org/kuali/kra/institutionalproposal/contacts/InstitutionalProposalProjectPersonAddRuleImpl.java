/*
 * Copyright 2005-2014 The Kuali Foundation
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
package org.kuali.kra.institutionalproposal.contacts;

import org.kuali.kra.institutionalproposal.document.InstitutionalProposalDocument;
import org.kuali.kra.institutionalproposal.home.InstitutionalProposal;
import org.kuali.rice.krad.util.GlobalVariables;


public class InstitutionalProposalProjectPersonAddRuleImpl extends BaseInstitutionalProposalContactAddRule implements
        InstitutionalProposalProjectPersonAddRule {

    @Override
    public boolean processAddInstitutionalProposalProjectPersonBusinessRules(InstitutionalProposalProjectPersonRuleAddEvent event) {
        InstitutionalProposalPerson newProjectPerson = event.getNewProjectPerson();
        InstitutionalProposal institutionalProposal = ((InstitutionalProposalDocument) event.getDocument()).getInstitutionalProposal();
        
        return checkForSelectedContactAndRole(newProjectPerson) && (checkForExistingPrincipalInvestigators(institutionalProposal, newProjectPerson) 
                                                                    & checkForDuplicatePerson(institutionalProposal, newProjectPerson));
    }
    
    boolean checkForSelectedContactAndRole(InstitutionalProposalContact newContact) {
        return super.checkForSelectedContactAndRole(newContact, PROPOSAL_PROJECT_PERSON_LIST_ERROR_KEY);
    }
    
    /**
     * Verify a PI exists
     * @param institutionalProposal
     * @param newProjectPerson
     * @return
     */
    boolean checkForExistingPrincipalInvestigators(InstitutionalProposal institutionalProposal, InstitutionalProposalPerson newProjectPerson) {
        boolean valid = true;
        if(newProjectPerson.isPrincipalInvestigator()) {
            for(InstitutionalProposalPerson p: institutionalProposal.getProjectPersons()) {
                if(p.isPrincipalInvestigator()) {
                    valid = false;
                    break;
                }
            }
        }
        
        if(!valid) {
            GlobalVariables.getMessageMap().putError(PROPOSAL_PROJECT_PERSON_LIST_ERROR_KEY, ERROR_PROPOSAL_PROJECT_PERSON_PI_EXISTS);
        }
        
        return valid;
    }

    /**
     * Verify no duplicate person
     * @param institutionalProposal
     * @param newProjectPerson
     * @return
     */
    boolean checkForDuplicatePerson(InstitutionalProposal institutionalProposal, InstitutionalProposalPerson newProjectPerson) {
        boolean valid = true;
        for(InstitutionalProposalPerson p: institutionalProposal.getProjectPersons()) {
            if (p.getClass().equals(newProjectPerson.getClass()) 
                    && p.getContact().getIdentifier().equals(newProjectPerson.getContact().getIdentifier())
                    && isImportantPerson(p) && isImportantPerson(newProjectPerson)) {
                valid = false;
                break;
            }
        }
        
        if(!valid) {
            GlobalVariables.getMessageMap().putError(PROPOSAL_PROJECT_PERSON_LIST_ERROR_KEY, ERROR_PROPOSAL_PROJECT_PERSON_EXISTS, 
                                                                                newProjectPerson.getContact().getFullName());
        }

        return valid;
    }
    
    private boolean isProjectPersonInvestigator(InstitutionalProposalPerson projectPerson) {
        return projectPerson.isCoInvestigator() || projectPerson.isPrincipalInvestigator();
    }

    private boolean isImportantPerson(InstitutionalProposalPerson person) {
        return isProjectPersonInvestigator(person) || person.isKeyPerson() || person.isMultiplePi();
    }
}
