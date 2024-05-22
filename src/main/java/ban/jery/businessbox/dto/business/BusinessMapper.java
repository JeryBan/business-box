package ban.jery.businessbox.dto.business;

import ban.jery.businessbox.model.Business;
import ban.jery.businessbox.model.User;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

@UtilityClass
public class BusinessMapper {

    public static Business mapToBusiness(BusinessInsertDTO dto, User user) {
        return new Business(null, dto.getName(), user, new HashSet<>(), new HashSet<>(), new ArrayList<>());
    }

    public static BusinessRoDTO mapToRoBusiness(Business business) {
        return new BusinessRoDTO(business.getId(), business.getName());
    }
}
