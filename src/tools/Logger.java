package src.tools;

import java.io.*;
import java.text.DateFormat;
import java.util.Date;

/**
 * <b>Logger est la classe qui permet de tracer l'éxecution d'une session d'exécution</b><br>
 * <p>
 * Logger possède deux attributs et cinq constantes qui servent aux méthodes<br>
 * de la classe :<br>
 * </p>
 * <ul>
 * <li>Un entier qui symbolise le niveau minimum de priorité des messages à tracer<br></li>
 * <li>Un <b>PrintWriter</b> qui permet d'écrire les logs<br></li>
 * <li>Cinq constantes qui définissent les niveaux de priorité des messages<br></li>
 * </ul>
 * <p>
 * Cette classe possède plusieurs constructeurs, en fonction qu'elle doive récupérer<br>
 * sa <i>configuration</i> dans un fichier ou utiliser des valeurs par défaut.<br>
 * Elle possède également la méthode <i>log</i> de traçage.<br>
 * </p>
 * 
 * @author Ewald JANIN
 * 
 * @see #ALL
 * @see #DEBUG
 * @see #INFO
 * @see #IMPORTANT
 * @see #OFF
 * @see #level
 * @see #mypw
 * @see src.tools.Logger#Logger(String)
 * @see src.tools.Logger#Logger(int, String)
 * @see src.tools.Logger#Logger(String, String)
 * @see src.tools.Logger#log(int, String)
 * @see src.tools.Logger#dispose()
 */
public class Logger {
	
	/**
	 * <b>ALL, constante valant 0</b><br>
	 * <p>
	 * Définit le niveau de priorité le plus bas.<br>
	 * Constante définie de manière publique et statique pour être<br>
	 * accédée depuis les autres classes<br>
	 * </p>
	 */
	public static final int ALL = 0;
	
	/**
	 * <b>DEBUG, constante valant 100</b><br>
	 * <p>
	 * Définit le niveau de priorité par défaut.<br>
	 * Constante définie de manière publique et statique pour être<br>
	 * accédée depuis les autres classes<br>
	 * </p>
	 */
	public static final int DEBUG = 100;
	
	/**
	 * <b>INFO, constante valant 500</b><br>
	 * <p>
	 * Définit le niveau de priorité intermédiaire.<br>
	 * Constante définie de manière publique et statique pour être<br>
	 * accédée depuis les autres classes<br>
	 * </p>
	 */
	public static final int INFO = 500;
	
	/**
	 * <b>ALL, constante valant 900</b><br>
	 * <p>
	 * Définit le niveau de priorité haut.<br>
	 * Constante définie de manière publique et statique pour être<br>
	 * accédée depuis les autres classes<br>
	 * </p>
	 */
	public static final int IMPORTANT = 900;
	
	/**
	 * <b>ALL, constante valant Integer.MAX_VALUE</b><br>
	 * <p>
	 * Définit le niveau de priorité maximum.<br>
	 * Constante définie de manière publique et statique pour être<br>
	 * accédée depuis les autres classes<br>
	 * </p>
	 */
	public static final int OFF = Integer.MAX_VALUE;
	
	/**
	 * <b>Entier définissant le seuil minimum de priorité à tracer<br></b>
	 * <p>
	 * Est utilisé dans la méthode <b>log</b> où il est comparé au niveau de<br>
	 * priorité du message demandé à tracer. Sa valeur par défaut correspond<br>
	 * à la constante <i>DEBUG</i>, mais il peut être défini dans le fichier<br>
	 * passé en configuration d'un constructeur de <b>Logger</b>.
	 * </p>
	 * @see #DEBUG
	 * @see src.tools.Logger#log(int, String)
	 */
	private int level;
	
	/**
	 * <b>Writer de sortie, correspond soit à un fichier soit à un flux<br></b>
	 * <p>
	 * Il est initialisé dans les constructeurs de <b>Logger</b>, et est<br>
	 * utilisé dans la méthode <b>log</b> pour écrire dans un fichier/flux.<br>
	 * Par défaut, la sortie est <i>System.err</i>.
	 * </p>
	 * @see src.tools.Logger#log(int, String)
	 */
	private PrintWriter mypw;
	
	/**
	 * <b>String représentant l'identifiant de l'objet dont on écrit les logs<br></b>
	 * <p>
	 * Il est initialisé dans les constructeurs de <b>Logger</b>, et est<br>
	 * utilisé dans la méthode <b>log</b> pour écrire le nom dans les logs.<br>
	 * </p>
	 * @see src.tools.Logger#Logger(String)
	 * @see src.tools.Logger#Logger(int, String)
	 * @see src.tools.Logger#Logger(String, String)
	 * @see src.tools.Logger#log(int, String)
	 */
	private String object_identifier;
	
	/**
	 * <b>Constructeur de base de Logger<br></b>
	 * <p>
	 * Ce constructeur initialise les attributs à leurs valeurs<br>
	 * par défaut (<i>DEBUG</i> pour <b>level</b> et <i>Systemm.err</i><br> pour <b>mypw</b><br>
	 *
	 * @param name
	 * 			Le nom de l'objet dont on écrit les logs
	 * 
	 * @see src.tools.Logger#level
	 * @see src.tools.Logger#mypw
	 * @see src.tools.Logger#Logger(int, String)
	 */
	public Logger(String name) {
		this(Logger.DEBUG, name);
	}
	
	/**
	 * <b>Constructeur de Logger<br></b>
	 * <p>
	 * Ce constructeur initialise les attributs, la valeur du <b>level</b> est passée<br>
	 * en argument. <b>mypw</b> est initialisé avec pour sortie <i>Systemm.err</i>.
	 * 
	 * @param niveau
	 * 			Le niveau minimum qu'il faudra afficher, pas les priorités inférieures.
	 * @param name
	 * 			Le nom de l'objet dont on écrit les logs
	 * @see src.tools.Logger#level
	 * @see src.tools.Logger#mypw
	 */
	public Logger(int niveau, String name) {
		this.object_identifier = name;
		this.level = niveau;
		this.mypw = new PrintWriter(System.err);
		this.log(src.tools.Logger.DEBUG, "Création d'un nouveau Logger sans fichier de configuration, level=" + this.level + ", flux de sortie : System.err");
	}
	
	/**
	 * <b>Constructeur de Logger<br></b>
	 * <p>
	 * Ce constructeur initialise les attributs, la valeur du <b>level</b> est celle<br>
	 * par d�faut. <b>mypw</b> est initialisé avec pour sortie ce qui est passé en <br>
	 * coniguration dans le fichier passé en argument, que ce soit un flux ou un fichier.<br>
	 * 
	 * @param file
	 * 			Le fichier de configuration dont les paramètres sont à extraire.
	 * @param name
	 * 			Le nom de l'objet dont on écrit les logs
	 * @see src.tools.Logger#level
	 * @see src.tools.Logger#mypw
	 */
	public Logger(String file, String name) {
		this.object_identifier = name;
		this.level = src.tools.Logger.DEBUG; //Niveau par défaut
		try {
			File config = new File(file);
			if(config.exists()) { //Si le fichier de configuration spécifié existe bien
				BufferedReader br = new BufferedReader(new FileReader(config));
				String lignelue = br.readLine();
				if(!(lignelue == null)) {	//SI le fichier de configuration n'est pas vide
					switch (lignelue) {		//Définition du niveau avec le fichier de configuration
					case "ALL":
						this.level = src.tools.Logger.ALL;
						break;
						
					case "DEBUG":
						this.level = src.tools.Logger.DEBUG;
						break;
						
					case "INFO":
						this.level = src.tools.Logger.INFO;
						break;
						
					case "IMPORTANT":
						this.level = src.tools.Logger.IMPORTANT;
						break;
						
					case "OFF":
						this.level = src.tools.Logger.OFF;
						break;				
					}
					
					lignelue = br.readLine();
					br.close();
					
					if(!(lignelue == null)) {	//Si la sortie a bien été spécifiée
						switch(lignelue) {		//Définition de la sortie
						case "System.err":
							this.mypw = new PrintWriter(System.err);
							break;
							
						case "System.out":
							this.mypw = new PrintWriter(System.out);
							break;
						
						default :	//Si ce n'est pas un flux, c'est un fichier
							try {
								File logs = new File(object_identifier + lignelue);
								logs.createNewFile();	//On tente de créer le fichier qui servira à enregistrer les logs
								this.mypw = new PrintWriter(new FileWriter(logs));
								this.log(src.tools.Logger.DEBUG, "Création d'un nouveau Logger avec fichier de configuration, level = " + this.level + ", flux de sortie : " + object_identifier + lignelue);
							} catch (Exception e) {
								e.printStackTrace();
								this.mypw = new PrintWriter(System.err);
								this.log(src.tools.Logger.OFF, "ERREUR : Impossible de créer un Logger avec la sortie spécifiée dans la configuration, création par défaut : level = " + this.level + ", flux de sortie : System.err");
							}
						}
					}
					else {	//Si la configuration de la sortie est nulle
						this.mypw = new PrintWriter(System.err);
						this.log(src.tools.Logger.OFF, "Impossible de créer un Logger avec le fichier spécifié dans la configuration, flux de sortie : System.err");
					}
				}
				else {	//Si le fichier de configuration est vide
					this.level = src.tools.Logger.DEBUG;
					this.mypw = new PrintWriter(System.err);
					this.log(src.tools.Logger.OFF, "ERREUR : fichier config spécifié illisible, création Logger défaut : level=" + this.level + ", flux de sortie : System.err");

				}
			}
			else {	//Si le fichier de configuration est inexistant
				this.level = src.tools.Logger.DEBUG;
				this.mypw = new PrintWriter(System.err);
				this.log(src.tools.Logger.OFF, "ERREUR : fichier config spécifié introuvable, création Logger défaut : level=" + this.level + ", flux de sortie : System.err");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * <b>Méthode qui fait le logging des messages<br></b>
	 * <p>
	 * Si le niveau de priorité du message passé en argument est<br>
	 * supérieur ou égal au niveau minimum de priorité à afficher<br>
	 * d�fini dans le <b>Logger</b>, alors le message est envoyé<br>
	 * sur la sortie choisie avec le <b>FileWriter</b> myfw de la classe.<br>
	 * </p>
	 * 
	 * @param level
	 * 			Niveau de priorité du message à tracer.
	 * @param message
	 * 			Nouveau message à tracer.
	 * 
	 * @see src.tools.Logger#level
	 * @see src.tools.Logger#mypw
	 */
	public void log(int level, String message) {
		try {
			if (level >= this.level) {	//Si le niveau de priorité autorise bien à tracer ce message
				switch(level) {	//On écrit le message sur la sortie, avec un nombre de tabulations selon sa priorité, pour plus de lisibilité, et avec l'heure, pour plus de traçabilité
				case src.tools.Logger.ALL:
					mypw.println("				" + object_identifier + " :: " + message + " --- Date : " + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(new Date()));
					mypw.flush();
					break;
					
				case src.tools.Logger.DEBUG:
					mypw.println("			" + object_identifier + " :: " + message + " --- Date : " + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(new Date()));
					mypw.flush();
					break;
					
				case src.tools.Logger.INFO:
					mypw.println("		" + object_identifier + " :: " + message + " --- Date : " + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(new Date()));
					mypw.flush();
					break;
					
				case src.tools.Logger.IMPORTANT:
					mypw.println("	" + object_identifier + " :: " + message + " --- Date : " + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(new Date()));
					mypw.flush();
					break;
					
				case src.tools.Logger.OFF:
					mypw.println(object_identifier + " :: " + message + " --- Date : " + DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(new Date()));
					mypw.flush();
					break;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <b>Méthode pour quitter une session<br></b>
	 * <p>
	 * Permet de fermer correctement le flux d'écriture.<br>
	 * </p>
	 */
	public void dispose() {
		this.log(src.tools.Logger.OFF, "Fermeture du Logger !");
		mypw.close();
	}

}
