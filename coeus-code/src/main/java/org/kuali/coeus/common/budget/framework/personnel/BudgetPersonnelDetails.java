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
package org.kuali.coeus.common.budget.framework.personnel;

import org.kuali.coeus.common.budget.framework.nonpersonnel.BudgetFormulatedCostDetail;
import org.kuali.coeus.sys.api.model.ScaleTwoDecimal;
import org.kuali.coeus.common.budget.framework.nonpersonnel.AbstractBudgetCalculatedAmount;
import org.kuali.coeus.common.budget.framework.nonpersonnel.BudgetLineItemBase;
import org.kuali.coeus.common.budget.framework.copy.DeepCopyIgnore;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import javax.persistence.*;

import org.apache.commons.collections4.CollectionUtils;
import org.kuali.coeus.common.budget.framework.core.BudgetService;
import org.kuali.coeus.common.budget.framework.core.CostElement;
import org.kuali.coeus.common.budget.framework.core.category.BudgetCategory;
import org.kuali.coeus.common.budget.framework.period.BudgetPeriod;
import org.kuali.coeus.sys.framework.persistence.BooleanNFConverter;
import org.kuali.coeus.sys.framework.persistence.ScaleTwoDecimalConverter;
import org.kuali.coeus.sys.framework.service.KcServiceLocator;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krad.data.DataObjectService;
import org.kuali.rice.krad.data.jpa.PortableSequenceGenerator;
import org.kuali.rice.krad.data.jpa.converters.BooleanYNConverter;
import org.kuali.rice.krad.util.ObjectUtils;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;

@Entity
@Table(name = "BUDGET_PERSONNEL_DETAILS")
public class BudgetPersonnelDetails extends BudgetLineItemBase {

    @Transient
    private Long budgetPeriodId;

    @Transient
    private String costElement;

    @Transient
    private ScaleTwoDecimal lineItemCost = ScaleTwoDecimal.ZERO;

    @Transient
    private String budgetCategoryCode;

    @Transient
    private Integer basedOnLineItem;

    @Transient
    private Integer quantity;

    @Transient
    private BudgetCategory budgetCategory;

    @Transient
    private Integer lineItemSequence;


    @Transient
    private CostElement costElementBO;

    @Transient
    private ScaleTwoDecimal totalCostSharingAmount;

    @Transient
    private String groupName;

    @Transient
    private Boolean formulatedCostElementFlag;

    @Transient
    private List<BudgetFormulatedCostDetail> budgetFormulatedCosts;

    //ignore the budget period bo during deep copy as any link up the budget object graph
    //will cause generateAllPeriods to consume large amounts of memory
    @DeepCopyIgnore
    @Transient
    private BudgetPeriod budgetPeriodBO;

    @DeepCopyIgnore
    @PortableSequenceGenerator(name = "SEQ_BUDGET_PER_DET_ID")
    @GeneratedValue(generator = "SEQ_BUDGET_PER_DET_ID")
    @Id
    @Column(name = "BUDGET_PERSONNEL_DETAILS_ID")
    private Long budgetPersonnelLineItemId;

    @DeepCopyIgnore
    @Column(name = "BUDGET_DETAILS_ID")
    @Id
    private Long budgetLineItemId; 

    @Column(name = "LINE_ITEM_NUMBER")
    private Integer lineItemNumber; 

    @Column(name = "BUDGET_ID")
    private Long budgetId;

    @Column(name = "BUDGET_PERIOD")
    private Integer budgetPeriod; 

    @Column(name = "ON_OFF_CAMPUS_FLAG")
    @Convert(converter = BooleanNFConverter.class)
    private Boolean onOffCampusFlag;

    @Column(name = "END_DATE")
    private Date endDate; 

    @Column(name = "START_DATE")
    private Date startDate;     

    @Column(name = "BUDGET_JUSTIFICATION")
    @Lob
    private String budgetJustification; 

    @Column(name = "COST_SHARING_AMOUNT")
    @Convert(converter = ScaleTwoDecimalConverter.class)
    private ScaleTwoDecimal costSharingAmount = ScaleTwoDecimal.ZERO; 

    @Column(name = "LINE_ITEM_DESCRIPTION")
    private String lineItemDescription; 

    @Column(name = "APPLY_IN_RATE_FLAG")
    @Convert(converter = BooleanYNConverter.class)
    private Boolean applyInRateFlag; 

    @Column(name = "PERSON_NUMBER")
    private Integer personNumber;

    @Column(name = "COST_SHARING_PERCENT")
    @Convert(converter = ScaleTwoDecimalConverter.class)
    private ScaleTwoDecimal costSharingPercent = ScaleTwoDecimal.ZERO;

    @Column(name = "JOB_CODE")
    private String jobCode;

    @Column(name = "PERCENT_CHARGED")
    @Convert(converter = ScaleTwoDecimalConverter.class)
    private ScaleTwoDecimal percentCharged = ScaleTwoDecimal.ZERO;

    @Column(name = "PERCENT_EFFORT")
    @Convert(converter = ScaleTwoDecimalConverter.class)
    private ScaleTwoDecimal percentEffort = ScaleTwoDecimal.ZERO;

    @Column(name = "PERIOD_TYPE")
    private String periodTypeCode;

    @Column(name = "PERSON_ID")
    private String personId;

    @Column(name = "SALARY_REQUESTED")
    @Convert(converter = ScaleTwoDecimalConverter.class)
    private ScaleTwoDecimal salaryRequested = ScaleTwoDecimal.ZERO;

    @Column(name = "SEQUENCE_NUMBER")
    private Integer sequenceNumber;

    @Column(name = "PERSON_SEQUENCE_NUMBER")
    private Integer personSequenceNumber;

    @Column(name = "UNDERRECOVERY_AMOUNT")
    @Convert(converter = ScaleTwoDecimalConverter.class)
    private ScaleTwoDecimal underrecoveryAmount = ScaleTwoDecimal.ZERO; 

    @Column(name = "SUBMIT_COST_SHARING")
    @Convert(converter = BooleanYNConverter.class)
    private Boolean submitCostSharingFlag = Boolean.TRUE; 

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    @JoinColumns({ @JoinColumn(name = "BUDGET_ID", referencedColumnName = "BUDGET_ID", insertable = false, updatable = false), @JoinColumn(name = "PERSON_SEQUENCE_NUMBER", referencedColumnName = "PERSON_SEQUENCE_NUMBER", insertable = false, updatable = false) })
    private BudgetPerson budgetPerson;

    @Transient
    private List<BudgetPersonnelCalculatedAmount> budgetPersonnelCalculatedAmounts;

    @Transient
    private List<BudgetPersonnelRateAndBase> budgetPersonnelRateAndBaseList;

    @Transient
    private List<BudgetPersonSalaryDetails> budgetPersonSalaryDetails;

    @Transient
    private transient DataObjectService dataObjectService;

    public BudgetPersonnelDetails() {
        budgetPersonnelCalculatedAmounts = new ArrayList<BudgetPersonnelCalculatedAmount>();
        budgetPersonnelRateAndBaseList = new ArrayList<BudgetPersonnelRateAndBase>();
        budgetPersonSalaryDetails = new ArrayList<BudgetPersonSalaryDetails>();
        budgetFormulatedCosts = new ArrayList<BudgetFormulatedCostDetail>();
    }

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public Integer getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(Integer personNumber) {
        this.personNumber = personNumber;
    }

    public ScaleTwoDecimal getCostSharingPercent() {
        return costSharingPercent;
    }

    public void setCostSharingPercent(ScaleTwoDecimal costSharingPercent) {
        this.costSharingPercent = costSharingPercent;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public ScaleTwoDecimal getPercentCharged() {
        return ScaleTwoDecimal.returnZeroIfNull(percentCharged);
    }

    public void setPercentCharged(ScaleTwoDecimal percentCharged) {
        this.percentCharged = percentCharged;
    }

    public ScaleTwoDecimal getPercentEffort() {
        return ScaleTwoDecimal.returnZeroIfNull(percentEffort);
    }

    public void setPercentEffort(ScaleTwoDecimal percentEffort) {
        this.percentEffort = percentEffort;
    }

    public String getPeriodTypeCode() {
        return periodTypeCode;
    }

    public void setPeriodTypeCode(String periodTypeCode) {
        this.periodTypeCode = periodTypeCode;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public ScaleTwoDecimal getSalaryRequested() {
        return salaryRequested;
    }

    public void setSalaryRequested(ScaleTwoDecimal salaryRequested) {
        this.salaryRequested = salaryRequested;
    }

    public ScaleTwoDecimal getCalculatedBaseSalary() {
        return calculatedBaseSalary;
    }

    public void setCalculatedBaseSalary(ScaleTwoDecimal calculatedBaseSalary) {
        this.calculatedBaseSalary = calculatedBaseSalary;
    }

    @Transient
    private ScaleTwoDecimal calculatedBaseSalary = ScaleTwoDecimal.ZERO;

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    /**
     * Gets the nonEmployeeFlag attribute. 
     * @return Returns the nonEmployeeFlag.
     */
    public Boolean getNonEmployeeFlag() {
        return getBudgetPerson() == null ? false : getBudgetPerson().getNonEmployeeFlag();
    }

    /**
     * Gets the personSequenceNumber attribute. 
     * @return Returns the personSequenceNumber.
     */
    public Integer getPersonSequenceNumber() {
        return personSequenceNumber;
    }

    /**
     * Sets the personSequenceNumber attribute value.
     * @param personSequenceNumber The personSequenceNumber to set.
     */
    public void setPersonSequenceNumber(Integer personSequenceNumber) {
        this.personSequenceNumber = personSequenceNumber;
    }

    /**
     * Gets the budgetPerson attribute. 
     * @return Returns the budgetPerson.
     */
    public BudgetPerson getBudgetPerson() {
        return budgetPerson;
    }

    /**
     * Sets the budgetPerson attribute value.
     * @param budgetPerson The budgetPerson to set.
     */
    public void setBudgetPerson(BudgetPerson budgetPerson) {
        this.budgetPerson = budgetPerson;
    }

    /**
     * Gets the budgetPersonnelCalculatedAmounts attribute. 
     * @return Returns the budgetPersonnelCalculatedAmounts.
     */
    public List<BudgetPersonnelCalculatedAmount> getBudgetPersonnelCalculatedAmounts() {
        if (CollectionUtils.isEmpty(budgetPersonnelCalculatedAmounts)) {
            budgetPersonnelCalculatedAmounts = getDataObjectService().findMatching(BudgetPersonnelCalculatedAmount.class, QueryByCriteria.Builder.andAttributes(Collections.singletonMap("budgetPersonnelLineItemId", budgetPersonnelLineItemId)).build()).getResults();
        }

        return budgetPersonnelCalculatedAmounts;
    }

    public List getBudgetCalculatedAmounts() {
        return getBudgetPersonnelCalculatedAmounts();
    }

    /**
     * Gets the budgetPersonnelRateAndBaseList attribute. 
     * @return Returns the budgetPersonnelRateAndBaseList.
     */
    public List<BudgetPersonnelRateAndBase> getBudgetPersonnelRateAndBaseList() {
        if (CollectionUtils.isEmpty(budgetPersonnelRateAndBaseList)) {
            budgetPersonnelRateAndBaseList = getDataObjectService().findMatching(BudgetPersonnelRateAndBase.class, QueryByCriteria.Builder.andAttributes(Collections.singletonMap("budgetPersonnelLineItemId", budgetPersonnelLineItemId)).build()).getResults();
        }

        return budgetPersonnelRateAndBaseList;
    }

    public List<BudgetPersonSalaryDetails> getBudgetPersonSalaryDetails() {
        return budgetPersonSalaryDetails;
    }

    public void setBudgetPersonSalaryDetails(List<BudgetPersonSalaryDetails> budgetPersonSalaryDetails) {
        this.budgetPersonSalaryDetails = budgetPersonSalaryDetails;
    }

    public String getEffdtAfterStartdtMsg() {
        this.refreshReferenceObject("budgetPerson");
        if (getStartDate() != null && budgetPerson.getEffectiveDate().after(getStartDate())) {
            return "Earning Period Start Date is before " + budgetPerson.getPersonName() + "'s Salary Effective Date. Salary is calculated based on Effective Date.";
        }
        return "";
    }

    public ScaleTwoDecimal getPersonMonths() {
        ScaleTwoDecimal result = null;
        if (getStartDate() != null && getEndDate() != null) {
            Calendar startDateCalendar = Calendar.getInstance();
            startDateCalendar.setTime(getStartDate());
            int startMonth = startDateCalendar.get(MONTH);
            Calendar endDateCalendar = Calendar.getInstance();
            endDateCalendar.setTime(getEndDate());
            double personMonths = 0d;
            while (startDateCalendar.compareTo(endDateCalendar) <= 0) {
                double noOfDaysInMonth = startDateCalendar.getActualMaximum(DAY_OF_MONTH);
                double noOfActualDays = 0;
                if (startDateCalendar.get(MONTH) == endDateCalendar.get(MONTH) && startDateCalendar.get(Calendar.YEAR) == endDateCalendar.get(Calendar.YEAR)) {
                    noOfActualDays = endDateCalendar.get(DAY_OF_MONTH) - startDateCalendar.get(DAY_OF_MONTH) + 1;
                } else if (startDateCalendar.get(MONTH) == startMonth) {
                    noOfActualDays = noOfDaysInMonth - startDateCalendar.get(DAY_OF_MONTH) + 1;
                } else {
                    noOfActualDays = noOfDaysInMonth;
                }
                startDateCalendar.add(MONTH, 1);
                startDateCalendar.set(DAY_OF_MONTH, 1);
                personMonths += (noOfActualDays / noOfDaysInMonth);
            }
            result = new ScaleTwoDecimal(new BigDecimal(personMonths).setScale(2, RoundingMode.HALF_UP).multiply(getPercentEffort().bigDecimalValue()).divide(new ScaleTwoDecimal(100).bigDecimalValue(), RoundingMode.HALF_UP));
        }
        return result;
    }

    /**
     * Gets the budgetPersonnelLineItemId attribute. 
     * @return Returns the budgetPersonnelLineItemId.
     */
    public Long getBudgetPersonnelLineItemId() {
        return budgetPersonnelLineItemId;
    }

    /**
     * Sets the budgetPersonnelLineItemId attribute value.
     * @param budgetPersonnelLineItemId The budgetPersonnelLineItemId to set.
     */
    public void setBudgetPersonnelLineItemId(Long budgetPersonnelLineItemId) {
        this.budgetPersonnelLineItemId = budgetPersonnelLineItemId;
    }

    public Long getBudgetLineItemId() {
        return budgetLineItemId;
    }

    public void setBudgetLineItemId(Long budgetLineItemId) {
        this.budgetLineItemId = budgetLineItemId;
    }

    public Integer getLineItemNumber() {
        return lineItemNumber;
    }

    public void setLineItemNumber(Integer lineItemNumber) {
        this.lineItemNumber = lineItemNumber;
    }

    public Integer getBudgetPeriod() {
        return budgetPeriod;
    }

    public void setBudgetPeriod(Integer budgetPeriod) {
        this.budgetPeriod = budgetPeriod;
    }

    public Boolean getOnOffCampusFlag() {
        return onOffCampusFlag;
    }

    public void setOnOffCampusFlag(Boolean onOffCampusFlag) {
        this.onOffCampusFlag = onOffCampusFlag;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getBudgetJustification() {
        return budgetJustification;
    }

    public void setBudgetJustification(String budgetJustification) {
        this.budgetJustification = budgetJustification;
    }

    public String getLineItemDescription() {
        return lineItemDescription;
    }

    public void setLineItemDescription(String lineItemDescription) {
        this.lineItemDescription = lineItemDescription;
    }

    public Boolean getApplyInRateFlag() {
        return applyInRateFlag;
    }

    public void setApplyInRateFlag(Boolean applyInRateFlag) {
        this.applyInRateFlag = applyInRateFlag;
    }

    /**
     * 
     * This method creates new instance of BudgetPersonnelCalculatedAmount
     * @return
     */
    public AbstractBudgetCalculatedAmount getNewBudgetPersonnelCalculatedAmount() {
        return new BudgetPersonnelCalculatedAmount();
    }

    public DataObjectService getDataObjectService() {
        if (dataObjectService == null) {
            dataObjectService = KcServiceLocator.getService(DataObjectService.class);
        }
        return dataObjectService;
    }

    public ScaleTwoDecimal getCostSharingAmount() {
        return ScaleTwoDecimal.returnZeroIfNull(costSharingAmount);
    }

    public void setCostSharingAmount(ScaleTwoDecimal costSharingAmount) {
        this.costSharingAmount = costSharingAmount;
    }

    public ScaleTwoDecimal getLineItemCost() {
        return ScaleTwoDecimal.returnZeroIfNull(lineItemCost);
    }

    public void setLineItemCost(ScaleTwoDecimal lineItemCost) {
        this.lineItemCost = lineItemCost;
    }

    public ScaleTwoDecimal getUnderrecoveryAmount() {
        return ScaleTwoDecimal.returnZeroIfNull(underrecoveryAmount);
    }

    public void setUnderrecoveryAmount(ScaleTwoDecimal underrecoveryAmount) {
        this.underrecoveryAmount = underrecoveryAmount;
    }

    public ScaleTwoDecimal getTotalCostSharingAmount() {
        return ScaleTwoDecimal.returnZeroIfNull(totalCostSharingAmount);
    }

    public void setTotalCostSharingAmount(ScaleTwoDecimal totalCostSharingAmount) {
        this.totalCostSharingAmount = totalCostSharingAmount;
    }

    public void setSubmitCostSharingFlag(Boolean submitCostSharingFlag) {
        this.submitCostSharingFlag = submitCostSharingFlag;
    }

    public Boolean getSubmitCostSharingFlag() {
        if (ObjectUtils.isNull(budgetPeriodBO)) {
            this.refreshReferenceObject("budgetPeriodBO");
        }
        return (getBudgetPeriodBO() != null && getBudgetPeriodBO().getBudget().getSubmitCostSharingFlag()) ? submitCostSharingFlag : false;
    }

    public Boolean getFormulatedCostElementFlag() {
        return formulatedCostElementFlag == null ? Boolean.FALSE : formulatedCostElementFlag;
    }

    public void setFormulatedCostElementFlag(Boolean formulatedCostElementFlag) {
        this.formulatedCostElementFlag = formulatedCostElementFlag;
    }

    public String getCostElement() {
        return costElement;
    }

    public void setCostElement(String costElement) {
        this.costElement = costElement;
    }

    public String getBudgetCategoryCode() {
        return budgetCategoryCode;
    }

    public void setBudgetCategoryCode(String budgetCategoryCode) {
        this.budgetCategoryCode = budgetCategoryCode;
    }

    public Integer getBasedOnLineItem() {
        return basedOnLineItem;
    }

    public void setBasedOnLineItem(Integer basedOnLineItem) {
        this.basedOnLineItem = basedOnLineItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BudgetCategory getBudgetCategory() {
        return budgetCategory;
    }

    public void setBudgetCategory(BudgetCategory budgetCategory) {
        this.budgetCategory = budgetCategory;
    }

    public Integer getLineItemSequence() {
        return lineItemSequence;
    }

    public void setLineItemSequence(Integer lineItemSequence) {
        this.lineItemSequence = lineItemSequence;
    }

    public CostElement getCostElementBO() {
        return costElementBO;
    }

    public void setCostElementBO(CostElement costElementBO) {
        this.costElementBO = costElementBO;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<BudgetFormulatedCostDetail> getBudgetFormulatedCosts() {
        return budgetFormulatedCosts;
    }

    public void setBudgetFormulatedCosts(List<BudgetFormulatedCostDetail> budgetFormulatedCosts) {
        this.budgetFormulatedCosts = budgetFormulatedCosts;
    }

    public BudgetPeriod getBudgetPeriodBO() {
        return budgetPeriodBO;
    }

    public void setBudgetPeriodBO(BudgetPeriod budgetPeriodBO) {
        this.budgetPeriodBO = budgetPeriodBO;
    }

    public void setBudgetPersonnelCalculatedAmounts(List<BudgetPersonnelCalculatedAmount> budgetPersonnelCalculatedAmounts) {
        this.budgetPersonnelCalculatedAmounts = budgetPersonnelCalculatedAmounts;
    }

    public void setBudgetPersonnelRateAndBaseList(List<BudgetPersonnelRateAndBase> budgetPersonnelRateAndBaseList) {
        this.budgetPersonnelRateAndBaseList = budgetPersonnelRateAndBaseList;
    }

    public Long getBudgetPeriodId() {
        return budgetPeriodId;
    }

    public void setBudgetPeriodId(Long budgetPeriodId) {
        this.budgetPeriodId = budgetPeriodId;
    }
}