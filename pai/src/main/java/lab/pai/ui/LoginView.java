package lab.pai.ui;

import java.util.Collections;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value="login")
@PageTitle("Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6721629045832688962L;
	private LoginForm login = new LoginForm();
    Anchor anchor = new Anchor("/register", "Nie masz konta? ZAREJESTRUJ SIE");
    Anchor google = new Anchor("/oauth2/authorization/google", "Zaloguj się przy użyciu konta Google");
    Anchor github = new Anchor("/oauth2/authorization/github", "Zaloguj się przy użyciu  GitHub");
    public LoginView(){
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        login.setAction("login");
        add(new H1("Login"), login, anchor, google, github);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(!beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .getOrDefault("error", Collections.emptyList())
                .isEmpty()){
            login.setError(true);
        }
    }
    
}
