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
package org.kuali.kra.printing;

public class PersonSignaturePrintHelper {

    private float signatureTag;
    private float coordinateX;
    private float coordinateY;
    
    public float getSignatureTag() {
        return signatureTag;
    }
    
    public void setSignatureTag(float signatureTag) {
        this.signatureTag = signatureTag;
    }
    
    public float getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(float coordinateX) {
        this.coordinateX = coordinateX;
    }
    
    public float getCoordinateY() {
        return coordinateY;
    }
    
    public void setCoordinateY(float coordinateY) {
        this.coordinateY = coordinateY;
    }
    
    
}
