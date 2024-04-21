package ban.jery.businessbox.service.business;

import ban.jery.businessbox.dto.business.BusinessDeleteDTO;
import ban.jery.businessbox.dto.business.BusinessInsertDTO;
import ban.jery.businessbox.model.Business;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface IBusinessService {

    Business insertBusinessToUser(BusinessInsertDTO dto) throws EntityNotFoundException;

    Business removeBusinessFromUser(BusinessDeleteDTO dto) throws EntityNotFoundException;

    List<Business> getUserBusiness(String email) throws EntityNotFoundException;
}
