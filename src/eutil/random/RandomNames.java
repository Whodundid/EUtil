package eutil.random;

import eutil.storage.EArrayList;

/**
 * A collection of 250 unique, randomly generated names.
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public final class RandomNames {

	//----------------------------
	
	// prevent instaniation
	private RandomNames() {}
	
	//----------------------------
	
	private static EArrayList<String> names = new EArrayList();
	
	public static String get() { return names.get(RandomUtil.getRoll(0, names.size() - 1)); }
	
	//----------------------------
	
	static {
		names.add("Neva");
		names.add("Angelita");
		names.add("Ariel");
		names.add("Katrice");
		names.add("Rosamond");
		names.add("Iva");
		names.add("Brandie");
		names.add("Loura");
		names.add("Shanice");
		names.add("Yolonda");
		names.add("Donette");
		names.add("Lachelle");
		names.add("Kenneth");
		names.add("Kandace");
		names.add("Bryan");
		names.add("Zandra");
		names.add("Shenita");
		names.add("Kayleigh");
		names.add("Tawanda");
		names.add("Janeen");
		names.add("Leonarda");
		names.add("Earleen");
		names.add("Coral");
		names.add("Wenona");
		names.add("Ellena");
		names.add("Vanessa");
		names.add("Shandra");
		names.add("Matilda");
		names.add("Shawnda");
		names.add("Catherine");
		names.add("Lael");
		names.add("Jerilyn");
		names.add("Tayna");
		names.add("Lorriane");
		names.add("Jeanette");
		names.add("Carlee");
		names.add("Tiffaney");
		names.add("Rossie");
		names.add("Oralia");
		names.add("Mazie");
		names.add("Delfina");
		names.add("Mellie");
		names.add("Leoma");
		names.add("Leonor");
		names.add("Quiana");
		names.add("Lavon");
		names.add("Chia");
		names.add("Robby");
		names.add("Solange");
		names.add("Flor");
		names.add("Liane");
		names.add("Candyce");
		names.add("Anneliese");
		names.add("Emerson");
		names.add("Jonelle");
		names.add("Loraine");
		names.add("Ginger");
		names.add("Shanae");
		names.add("Melonie");
		names.add("Sallie");
		names.add("Carley");
		names.add("Maren");
		names.add("Branden");
		names.add("Ileana");
		names.add("Jame");
		names.add("Lizabeth");
		names.add("Hyman");
		names.add("Xenia");
		names.add("Cayla");
		names.add("Jerrold");
		names.add("Jasper");
		names.add("Aiko");
		names.add("Walton");
		names.add("Rosalia");
		names.add("Ula");
		names.add("Idella");
		names.add("Kam");
		names.add("Thelma");
		names.add("Alfredo");
		names.add("Lisette");
		names.add("Marty");
		names.add("Serita");
		names.add("Renee");
		names.add("Jennie");
		names.add("Tamatha");
		names.add("Gloria");
		names.add("Katie");
		names.add("Rikki");
		names.add("Elias");
		names.add("Monte");
		names.add("Sang");
		names.add("Elanor");
		names.add("Breana");
		names.add("Wendy");
		names.add("Marhta");
		names.add("Jodie");
		names.add("Rolando");
		names.add("Bessie");
		names.add("Mertie");
		names.add("Julienne");
		names.add("Royce");
		names.add("Britni");
		names.add("Nolan");
		names.add("Fredda");
		names.add("Darla");
		names.add("Lura");
		names.add("Melia");
		names.add("Zenobia");
		names.add("Kendall");
		names.add("Winnie");
		names.add("Carma");
		names.add("Shelley");
		names.add("Nia");
		names.add("Conrad");
		names.add("Therese");
		names.add("Tandy");
		names.add("Galen");
		names.add("Racheal");
		names.add("Lanny");
		names.add("Vilma");
		names.add("Dusty");
		names.add("Johnsie");
		names.add("Earlean");
		names.add("Coralee");
		names.add("Patricia");
		names.add("Tuyet");
		names.add("Milo");
		names.add("Camila");
		names.add("Ashton");
		names.add("Zada");
		names.add("Rachel");
		names.add("Garfield");
		names.add("Tawna");
		names.add("Carrol");
		names.add("Cecilia");
		names.add("Deeanna");
		names.add("Lexie");
		names.add("Eura");
		names.add("Bari");
		names.add("Loma");
		names.add("Daysi");
		names.add("Zenaida");
		names.add("Roxana");
		names.add("Ida");
		names.add("Pauletta");
		names.add("Melani");
		names.add("Niki");
		names.add("Shanta");
		names.add("Joana");
		names.add("Vicky");
		names.add("Luigi");
		names.add("Sophia");
		names.add("Emerald");
		names.add("Esta");
		names.add("Merrilee");
		names.add("Briana");
		names.add("Candida");
		names.add("Krystal");
		names.add("Sona");
		names.add("Janine");
		names.add("Homer");
		names.add("Daniele");
		names.add("Nelida");
		names.add("Fernanda");
		names.add("Sammy");
		names.add("Cyndi");
		names.add("Frida");
		names.add("Harrison");
		names.add("Tabetha");
		names.add("Gillian");
		names.add("Clemmie");
		names.add("Misti");
		names.add("Vince");
		names.add("Serena");
		names.add("Woodrow");
		names.add("Cynthia");
		names.add("Paige");
		names.add("Catarina");
		names.add("Stefanie");
		names.add("Jann");
		names.add("Marguerite");
		names.add("Tempie");
		names.add("Dorthy");
		names.add("Joelle");
		names.add("Alison");
		names.add("Winter");
		names.add("Virginia");
		names.add("Lorena");
		names.add("Hans");
		names.add("Delmy");
		names.add("Frederick");
		names.add("Mammie");
		names.add("Adrian");
		names.add("Ashely");
		names.add("Floria");
		names.add("Luther");
		names.add("Jama");
		names.add("Ngoc");
		names.add("Scotty");
	}
	
}
