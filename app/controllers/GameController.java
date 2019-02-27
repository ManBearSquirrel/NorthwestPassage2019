package controllers;

import play.Logger;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;

public class GameController extends Controller
{
    private FormFactory formFactory;
    private static final Logger.ALogger logger = Logger.of(GameController.class);

    private static final String PLAYER_NAME_SESSION_KEY = "PLAYER_NAME";
    private static final String CREW_COUNT_SESSION_KEY = "CREW_COUNT";
    private static final int STARTING_CREW_COUNT = 10;

    @Inject
    public GameController(FormFactory formFactory)
    {
        this.formFactory = formFactory;
    }

    public Result getWelcome()
    {
        logger.debug("getWelcome");
        return ok(views.html.welcome.render());
    }

    public Result postStart(Http.Request request)
    {
        DynamicForm form = formFactory.form().bindFromRequest(request);
        String playerName = form.get("playerName");

        return ok(views.html.start.render())
                 .addingToSession(request, PLAYER_NAME_SESSION_KEY, playerName)
                 .addingToSession(request, CREW_COUNT_SESSION_KEY, String.valueOf(STARTING_CREW_COUNT));
    }

    public Result postRestart()
    {
        return ok(views.html.start.render());
    }

    public Result postEastFromEngland(Http.Request request)
    {
        String crewCount = getDecrementedCrewCount(request);

        return ok(views.html.eastfromengland.render(crewCount)).addingToSession(request, CREW_COUNT_SESSION_KEY, crewCount);
    }

    private String getDecrementedCrewCount(Http.Request request)
    {
        String crewCountAsString = request.session().getOptional(CREW_COUNT_SESSION_KEY).orElse(String.valueOf(STARTING_CREW_COUNT));
        int crewCount = Integer.parseInt(crewCountAsString);
        crewCount--;
        return String.valueOf(crewCount);
    }

    public Result postNorthEnd(Http.Request request)
    {
        String playerName = request.session().getOptional(PLAYER_NAME_SESSION_KEY).orElse("Unknown");
        return ok(views.html.northend.render(playerName));
    }

    public Result postNorthFromEngland()
    {
        return ok(views.html.northfromengland.render());
    }

    public Result postWestFromEngland()
    {
        return ok(views.html.westfromengland.render());
    }

    public Result postEastEnd()
    {
        return ok(views.html.eastend.render());
    }

    public Result postWestEnd()
    {
        return ok(views.html.westend.render());
    }

    public Result postHomePort()
    {
        return ok(views.html.homeport.render());
    }

    public Result getKittens()
    {
        return ok(views.html.kittens.render());
    }

}
