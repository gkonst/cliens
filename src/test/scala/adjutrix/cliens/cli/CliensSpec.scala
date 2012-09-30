package adjutrix.cliens.cli

import org.scalatest.mock.MockitoSugar
import org.scalatest.FlatSpec
import org.specs2.matcher.MustMatchers
import adjutrix.cliens.model.Model
import org.mockito.Mockito._

class CliensSpec extends FlatSpec with MustMatchers with MockitoSugar {

  "list" should "call cli.list" in new CliensContext {
    cliens.main(Array("list", "category"))
    verify(cliFactory, times(1)).apply("category")
    verify(cli, times(1)).list(NoOption())
  }


  "view" should "call service.view" in new CliensContext {
    cliens.main(Array("view", "storage", "1"))
    verify(cliFactory, times(1)).apply("storage")
    verify(cli, times(1)).detail(1)(NoOption())
  }

  trait CliensContext {
    val cli = mock[ListCLI[Model]]
    val cliFactory = spy(new MockedServiceFactory)
    val cliens = new Cliens(cliFactory)

    class MockedServiceFactory extends CLIFactory {
      def apply(name: String) = cli
    }

  }

}
