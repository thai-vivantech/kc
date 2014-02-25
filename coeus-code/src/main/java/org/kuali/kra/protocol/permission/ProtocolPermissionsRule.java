/*
 * Copyright 2005-2013 The Kuali Foundation
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
package org.kuali.kra.protocol.permission;

import org.kuali.coeus.common.permissions.impl.rules.PermissionsRuleBase;
import org.kuali.kra.infrastructure.RoleConstants;

/**
 * Business Rule to determine the legality of modifying the access
 * to a ProtocolBase Document.
 * 
 * @author Kuali Research Administration Team (kualidev@oncourse.iu.edu)
 */
public class ProtocolPermissionsRule extends PermissionsRuleBase {
    
    /**
     * @see org.kuali.coeus.common.permissions.impl.rules.PermissionsRuleBase#getAdministratorRoleName()
     */
    @Override
    protected String getAdministratorRoleName() {
        return RoleConstants.PROTOCOL_AGGREGATOR;
    }   
}
