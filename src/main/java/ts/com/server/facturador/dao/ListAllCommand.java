package ts.com.server.facturador.dao;


import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sistema.facturador.persistence.Employee;

public class ListAllCommand extends HystrixCommand<List<Employee>> {
  private static final Logger log = LoggerFactory.getLogger(ListAllCommand.class);
  
  public ListAllCommand(Object employeeDao) {
    super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("DatabaseGroup")));
  }
  
  protected List<Employee> run() throws Exception {
    return Collections.emptyList();
  }
  
  protected List<Employee> getFallback() {
    log.error("Problemas al consultar la base de datos");
    return Collections.emptyList();
  }
}
