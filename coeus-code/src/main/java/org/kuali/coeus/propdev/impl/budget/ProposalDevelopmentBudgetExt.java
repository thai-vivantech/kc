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
package org.kuali.coeus.propdev.impl.budget;

import javax.persistence.*;

import org.kuali.coeus.common.budget.framework.core.Budget;

import java.sql.Timestamp;

/**
 * This class is just to hold the ProposalDevelopmentBudget. We should move PD Budget stuffs to this class later. 
 * Right now this is being used to represent extension class for Budget in repository.
 */
@Entity
@Table(name = "EPS_PROPOSAL_BUDGET_EXT")
@PrimaryKeyJoinColumn(name="BUDGET_ID", referencedColumnName="BUDGET_ID")
public class ProposalDevelopmentBudgetExt extends Budget {

    private static final long serialVersionUID = 8234453927894053540L;

    @Column(name = "HIERARCHY_HASH_CODE")
    private Integer hierarchyLastSyncHashCode;

    public Integer getHierarchyLastSyncHashCode() {
        return hierarchyLastSyncHashCode;
    }

    public void setHierarchyLastSyncHashCode(Integer hierarchyLastSyncHashCode) {
        this.hierarchyLastSyncHashCode = hierarchyLastSyncHashCode;
    }
}