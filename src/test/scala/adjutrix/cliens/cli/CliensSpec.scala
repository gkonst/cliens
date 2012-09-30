package adjutrix.cliens.cli

import org.specs2.mutable.Specification
import org.specs2.mock.Mockito

class CliensSpec extends Specification with Mockito {

  //  "list should call service.list" in new CliensScope {
  //    cliens.main(Array("list", "category"))
  //    there was one(serviceFactory).apply("category")
  //    there was one(service).list(NoOption())
  //  }
  //
  //  "view should call service.view" in new CliensScope {
  //    cliens.main(Array("view", "storage", "1"))
  //    there was one(serviceFactory).apply("storage")
  //    there was one(service).view(1, NoOption())
  //  }
  //
  //  trait CliensScope extends Scope {
  //    val service = mock[Service[Model]]
  //    val serviceFactory = spy(new MockedServiceFactory)
  //    val cliens = new Cliens(serviceFactory)
  //
  //    class MockedServiceFactory extends ServiceFactory {
  //      def apply(name: String) = service
  //    }
  //
  //  }

}
