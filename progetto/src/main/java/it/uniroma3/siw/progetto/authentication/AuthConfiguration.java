package it.uniroma3.siw.progetto.authentication;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter {

	/*
	 * punto di accesso diretto al DB per prendere le credenziali ed effettuare
	 * autenticazione e autorizazione.
	 * I due configure sono due serie di invocazioni "concatenate" all'oggetto http, che tendenzialmente restituiscono
	 * lo stesso oggetto http "aggiornato", in modo da poter concatenare le chiamate.
	 */
	@Autowired
	DataSource datasource;
	
	/* configura le politiche di autenticazione e autorizzazione */
	public void configure (HttpSecurity http) throws Exception {
		
		//AUTORIZZAZIONE
		
		//chi puo' accedere a quali pagine
		http.authorizeRequests()
			//chiunque puo' accedere a queste pagine
			.antMatchers(HttpMethod.GET, "/", "/index", "/login", "/register/user").permitAll()
			//chiunque puo' mandare richieste POST al login endpoint e al register endpoint
			.antMatchers(HttpMethod.POST, "/login", "/register/user").permitAll()
			//solo gli utenti autenticati con autorizzazione ADMIN posso accedere alla pagina admin
			.antMatchers(HttpMethod.GET, "/admin/home").hasAnyAuthority("ADMIN_ROLE")
			//tutti gli utenti autenticati posso accedere alle pagine rimanenti
			.anyRequest().authenticated()
			
			//AUTENTICAZIONE
			
			//login -> quale protocollo utilizzare
			.and().formLogin()
			//dopo che il login ha successo, si viene reindirizzati alla homepage
			.defaultSuccessUrl("/user/home")
			/*
			 * Con questa configurazione si ottiene una vista di login preconfezionata da Spring Security
			 * creando anche i mapping dei controller (i vari effetti sulle richieste GET o POST)
			 * Per modificare si utilizza .loginPage(), definendo in un controller come gestire la GET (non la POST)
			 */
			//logout
			.and().logout()
			//si attiva quando viene inviata una GET a "/logout"
			.logoutUrl("/logout")
			//successo -> reindirizzati ad "/index"
			.logoutSuccessUrl("/index")
			//interrompere la sessione sul logout
			.invalidateHttpSession(true)
			.clearAuthentication(true).permitAll();
			
	}

	
	/* specifica dove spring security deve trovare le credenziali nel DB */
	public void configure (AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication()
			//grazie a dataSource si accede alle credenziali salvate
			.dataSource(this.datasource)
			//ricerca username e role, per poi cercare username password e un flag booleano per uno user abilitato
			.authoritiesByUsernameQuery("SELECT username, role FROM Credential WHERE username=?")
			.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM Credential WHERE username=?");
	}
	
	
	/* utile a criptare e decriptare le password delle credenziali
	 * Bean -> l'oggetto restituito dal metodo viene salvato nel contesto dell'applicazione
	 * quando verra' richiamato, non ci sara' bisogno di rinizializzare l'oggetto, ma si utilizzera'
	 * la stessa instanza salvata nel contesto (@Component e' un tipo particolare di @Bean)
	 * Lo si puo' ottenere tramite @Autowired
	 *  */
	@Bean
	PasswordEncoder passwordEncoder() {
		//BCryptEncoder e' una variazione della cifratura di default
		return new BCryptPasswordEncoder();
	}
}
