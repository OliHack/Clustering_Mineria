package clustering;

public class MainClass {

	private static MainClass	miMainClass;
	private KmeansAlgorithm		kmeans;

	private MainClass() {

	}

	public void ejecutar() {
		kmeans = new KmeansAlgorithm(2, "singlelink", "aleatorio", 100, 0.2);
		kmeans.calcularKmeans();
		System.out.println("Indicie de calidad interna Shilhouette :" + kmeans.getSilhouetteAgrupamiento());
		
	}

	public static MainClass getMainClass() {
		if (MainClass.miMainClass == null) {
			MainClass.miMainClass = new MainClass();
		}
		return MainClass.miMainClass;
	}

}