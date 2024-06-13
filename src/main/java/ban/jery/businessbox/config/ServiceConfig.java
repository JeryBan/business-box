package ban.jery.businessbox.config;

import ban.jery.businessbox.repositories.*;
import ban.jery.businessbox.security.JwtService;
import ban.jery.businessbox.service.accSettings.AccSettingsServiceImpl;
import ban.jery.businessbox.service.accSettings.IAccSettingsService;
import ban.jery.businessbox.service.business.BusinessServiceImpl;
import ban.jery.businessbox.service.business.IBusinessService;
import ban.jery.businessbox.service.chat.ChatServiceImpl;
import ban.jery.businessbox.service.chat.IChatService;
import ban.jery.businessbox.service.employee.EmployeeServiceImpl;
import ban.jery.businessbox.service.employee.IEmployeeService;
import ban.jery.businessbox.service.product.IProductService;
import ban.jery.businessbox.service.product.ProductServiceImpl;
import ban.jery.businessbox.service.user.IUserService;
import ban.jery.businessbox.service.user.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public IUserService userService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }

    @Bean
    public IBusinessService businessService(UserRepository userRepository, BusinessRepository businessRepository) {
        return new BusinessServiceImpl(userRepository, businessRepository);
    }

    @Bean
    public IEmployeeService employeeService(BusinessRepository businessRepository, EmployeeRepository employeeRepository) {
        return new EmployeeServiceImpl(businessRepository, employeeRepository);
    }

    @Bean
    public IProductService productService(BusinessRepository businessRepository, ProductRepository productRepository) {
        return new ProductServiceImpl(businessRepository, productRepository);
    }

    @Bean
    public IChatService chatService(UserRepository userRepository, BusinessRepository businessRepository, ChatEntryRepository chatEntryRepository) {
        return new ChatServiceImpl(userRepository, businessRepository, chatEntryRepository);
    }

    @Bean
    public IAccSettingsService accSettingsService(UserRepository userRepository, AccSettingsRepository accSettingsRepository) {
        return new AccSettingsServiceImpl(userRepository, accSettingsRepository);
    }

}
