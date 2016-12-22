/**
 * Test class.
 *   
 * @author Mariana Azevedo
 * @since 13/07/2014
 *
 */
public class HelloWorld {

	private String name;
	private int age; 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		//Set the age
		this.age = age;
		System.out.println();
	}

	/**
	 * Metodo main
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*
		 * Comentario de bloco
		 */

		HelloWorld hw = new HelloWorld();
		if(true) {
			hw.setAge(10);
		}

	}
}