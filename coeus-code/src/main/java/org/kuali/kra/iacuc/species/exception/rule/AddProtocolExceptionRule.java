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
package org.kuali.kra.iacuc.species.exception.rule;

import org.kuali.rice.krad.rules.rule.BusinessRule;

/**
 * This class adds rule for adding new <code>ProtocolException</code> object
 */
public interface AddProtocolExceptionRule extends BusinessRule {
    
    /**
     * This method evaluates to true if ProtocolException objects satisfy required fields and business rules.
     * @param addProtocolExceptionEvent
     * @return boolean true for valid object and false for invalid entry
     */
    public boolean processAddProtocolExceptionBusinessRules(AddProtocolExceptionEvent addProtocolExceptionEvent);
    
}
