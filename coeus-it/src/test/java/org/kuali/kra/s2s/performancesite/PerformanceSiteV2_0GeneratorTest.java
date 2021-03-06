package  org.kuali.kra.s2s.performancesite;




import java.util.ArrayList;
import java.util.List;

import org.kuali.coeus.common.framework.org.Organization;
import org.kuali.coeus.common.framework.rolodex.Rolodex;
import org.kuali.coeus.propdev.impl.core.ProposalDevelopmentDocument;
import org.kuali.coeus.propdev.impl.location.CongressionalDistrict;
import org.kuali.coeus.propdev.impl.location.ProposalSite;
import org.kuali.coeus.s2sgen.impl.generate.support.PerformanceSiteV2_0Generator;
import org.kuali.kra.s2s.generator.S2STestBase;

public class PerformanceSiteV2_0GeneratorTest extends S2STestBase<PerformanceSiteV2_0Generator> {

	@Override
	protected void prepareData(ProposalDevelopmentDocument document)
			throws Exception {
	    
		List<ProposalSite> proposalSites = new ArrayList<ProposalSite>();
	    List<CongressionalDistrict> congressionalDistricts = new ArrayList<CongressionalDistrict>();
	    Rolodex rolodex =new Rolodex();
        rolodex.setRolodexId(1);
        rolodex.setAddressLine1("addressLine1");
        rolodex.setAddressLine2("addressLine2");
        rolodex.setAddressLine3("addressLine3");
        rolodex.setOwnedByUnit("000001");
        rolodex.setOrganization("University");
        rolodex.setSponsorAddressFlag(Boolean.FALSE);
        rolodex.setCreateUser("admin");
        rolodex.setCity("Camebridge");
        rolodex.setCounty(null);
        rolodex.setCountryCode("USA");
        rolodex.setPostalCode("02039-4307");
        rolodex.setState("MA");
	    int siteNumber =0;
	    Organization organization = new Organization();
        organization.setOrganizationName("University");
        organization.setOrganizationId("000001");
        organization.setContactAddressId(1);
        organization.setRolodex(rolodex);
			   
	    ProposalSite performingOrganization = new ProposalSite();
	    performingOrganization.setLocationTypeCode(2);
	    performingOrganization.setOrganization(organization);
	    performingOrganization.setSiteNumber(++siteNumber);
	    performingOrganization.setLocationName(organization.getOrganizationName());
	    performingOrganization.setDevelopmentProposal(document.getDevelopmentProposal());
	   
	    CongressionalDistrict congressionalDistrict = 	new CongressionalDistrict();
	    congressionalDistrict.setId((long) 001111111);
	    congressionalDistrict.setCongressionalDistrict("MA-008");
	    congressionalDistrict.setProposalSite(performingOrganization);	   
	    congressionalDistricts.add(congressionalDistrict);
	    performingOrganization.setCongressionalDistricts(congressionalDistricts);
	    proposalSites.add(performingOrganization);
	    document.getDevelopmentProposal().setProposalSites(proposalSites);
   
	}

	@Override
	protected String getFormGeneratorName() {
		return PerformanceSiteV2_0Generator.class.getSimpleName();
	}

}
