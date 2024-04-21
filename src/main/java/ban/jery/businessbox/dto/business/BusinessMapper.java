package ban.jery.businessbox.dto.business;

import ban.jery.businessbox.model.Business;
import ban.jery.businessbox.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BusinessMapper {

    public static Business mapToBusiness(BusinessInsertDTO dto, User user) {
        return new Business(null, dto.getName(), user);
    }

    public static BusinessRoDTO mapToRoBusiness(Business business) {
        return new BusinessRoDTO(business.getId(), business.getName());
    }
}
