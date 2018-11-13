package clustering;

import java.util.ArrayList;
import java.util.Iterator;

public class KmeansAlgorithm {
	// número de clusters
	private final int					k;
	
	//completelink o singlelink-->singlelink
	private final String				distInter;
	
	//aleatorio o division o 2kclusters-->aleatorio
	private final String				inicializacion;
	private final int					iteraciones;
	private final double				umbral;
	private final ArrayList<Cluster>	resultado;
	private final String				tipoDistancia;

	
	
	/**
	 * Constructora para la clase
	 * 
	 * @param pK
	 * @param pDist
	 *            //singlelink o completelink
	 * @param pInic
	 * @param pIter
	 * @param pUmbr
	 */
	public KmeansAlgorithm(int pK, String pDist, String pInic, int pIter, double pUmbr, String pTipoDist) {
		
		//Inicializaciones
		k = pK;
		distInter = pDist;
		inicializacion = pInic;
		iteraciones = pIter;
		resultado = new ArrayList<Cluster>();
		umbral = pUmbr;
		tipoDistancia = pTipoDist;
	}
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////               MÉTODOS                          /////////////////////////////////////////////////
///////////////////////////////              PARA CALCULAR                     /////////////////////////////////////////////////
///////////////////////////////             EL K-MEANS                        //////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Método principal para calcular el K-Means Clustering
	 */
	public void calcularKmeans() {
		
		//Inicializaciones de variables y asignaciones de clusters
		DataBase.getDataBase().inicializarInstancias();
		asignarVectorClusters();
		asignarInstanciasClusters();
		int i = 0;
		
		//Imprimimos por pantalla los valores iniciales seleccionados
		System.out.println("**¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*");
		System.out.println("**¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*");
		System.out.println("Valores iniciales clusters");
		System.out.println("**¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*");
		System.out.println("**¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*¿*");
		imprimirClusters();
		
		while (i < iteraciones) {
			
			ArrayList<String> list1 = this.resultado.get(0).getCentroide();
			recalcularCentroides();
			asignarInstanciasClusters();
			double dist = this.resultado.get(0).calcularDistancia(list1, tipoDistancia);
			System.out.println("Distancia iteración No" + i + ":  " + dist);
			
			if (dist <= umbral) {
				break;
			}
			i++;
		}
		
		//Imprimimos los datos de los Clusters
		System.out.println("***********************************");
		System.out.println("***********************************");
		System.out.println("Instancias totales de la muestra: " + DataBase.getDataBase().getInstancias().size());
		imprimirClusters();
	}

	
	/**
	 * Recalcula los centroides para la siguiente iteración.
	 */
	private void recalcularCentroides() {
		Iterator<Cluster> it = resultado.iterator();
		while (it.hasNext()) {
			it.next().recalcularCentroide();
		}

	}
	
	
	/**
	 * Asigna a una instancia el Clúster que esté más cerca
	 * @param pInst
	 */
	public void asignarInstancia(Instancia pInst) {
		
		//Inicializaciones del método
		int i = 0;
		int pos = 0;
		double distancia = this.resultado.get(0).calcularDistancia(pInst.getLista(), tipoDistancia);
		Iterator<Cluster> it = this.resultado.iterator();
		
		while (it.hasNext()) {
			Cluster nuevo = it.next();
			if (nuevo.calcularDistancia(pInst.getLista(), tipoDistancia) < distancia) {
				pos = i;
			}
			i++;
		}
		this.resultado.get(pos).addInstancia(pInst);
	}

	
	/**
	 * Asigna todas las instancias a sus correspondientes Clústers más cercanos
	 * 
	 */
	public void asignarInstanciasClusters() {

		Iterator<Instancia> it = DataBase.getDataBase().getInstancias().iterator();
		
		while (it.hasNext()) {
			Instancia inst = it.next();
			asignarInstancia(inst);
		}
	}

	/**
	 * Imprime los datos de los clusters por pantalla
	 */
	private void imprimirClusters(){
		
		Iterator<Cluster> it = resultado.iterator();
		
		while(it.hasNext()){
			
			it.next().printCentroide();
			it.next().printCluster();
		}
	}
	
	

	
	
	/**
	 * Inicializa los centroides iniciales
	 */
	public void asignarVectorAleatorioClusters() {
		
		int i = 0;
		System.out.println("Valores de los centroides iniciales: ");
		
		while (i < k) {
			
			Cluster nuevo = new Cluster(getVectorAleatorio());
			
			if (!resultado.contains(nuevo)) {
				
				resultado.add(nuevo);
				nuevo.printCentroide();
				i++;
			}
		}
	}
	
	
	/**
	 * Método para obtener un Vector Aleatorio
	 * @return
	 */
	public Instancia getVectorAleatorio() {

		return DataBase.getDataBase().getRandomVector();
	}
	
	
	/**
	 * Asignamos un vector a un Cluster dependiendo del tipo de inicialización
	 */
	public void asignarVectorClusters() {
		switch (inicializacion) {
		case "aleatorio":
			asignarVectorAleatorioClusters();
			break;
		case "division":
			asignarVectorDivisionClusters();
			break;
		case "2kclusters":
			asignarVector2kClusters();
			break;
		default:
			System.out.println("Error al asignar clusters");
			break;
		}
	}
	
	
	public void compararClustersInstancias() {
		// TODO - implement KmeansAlgorithm.compararClustersInstancias
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param pK
	 */
	public void crearClusters(int pK) {
		// TODO - implement KmeansAlgorithm.crearClusters
		throw new UnsupportedOperationException();
	}

	public ArrayList<Cluster> getKClustersMasAlejados(ArrayList<Cluster> pClusters) {
		/////////////////////// falta partir el metodo grande y seguir con el
		/////////////////////// calculo
		/////////////////////// los 2 primeros clusters hechos bien falta seguir
		/////////////////////// en funcion de ellos
		/////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
		return null;
	}
	
	
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////               MÉTODOS                          /////////////////////////////////////////////////
//////////////////////////////              PARA CALCULAR                      /////////////////////////////////////////////////
/////////////////////////////               EL SILHOUETTE                 /////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	/**
	 * Método para calcular el Silhouette
	 * @param pInstancia
	 * @param pCluster
	 * @return double
	 */
	public double getSilhouette(Instancia pInstancia, Cluster pCluster) {

		// Calculamos la cohesión
		double cohexion = pCluster.getDistanciaMedia(pInstancia, tipoDistancia);

		// Calculamos la separación
		Cluster masCercano = getClusterMasCercano(pCluster);
		double separacion = masCercano.getDistanciaMedia(pInstancia, tipoDistancia);

		// Comparamos la cohesión y la separación
		if (cohexion >= separacion)
			return (separacion - cohexion) / cohexion;
		else
			return (separacion - cohexion) / separacion;
	}
	
	/**
	 * Método principal para realizar el Silhouette
	 * @return double
	 */
	public double getSilhouetteAgrupamiento() {
		
		//Inicializaciones del método
		double shilhouette = 0;
		int i = 0;
		Iterator<Cluster> it = resultado.iterator();
		
		while (it.hasNext()) {
			
			Cluster c = it.next();
			Iterator<Instancia> it2 = c.getInstancias().getIterator();	
			
			while (it2.hasNext()) {		
				
				  Instancia inst = it2.next(); i = i + 1; double sh =
				  this.getSilhouette(inst, c); System.out.println(sh);
				  shilhouette = shilhouette + sh; //shilhouette = shilhouette +
				  this.getSilhouette(inst, c);				 
			}

			// sillhouete con 10%
			/*for (int j = 0; j < c.getInstancias().getInstancias().size() - 1; j++) {
				Instancia inst = c.getInstancias().getInstancias().get(j);
				j = (int) (j + (c.getInstancias().getInstancias().size() - 1) * 0.1);
				i = i + 1;
				double sh = this.getSilhouette(inst, c);
				System.out.println(sh);
				shilhouette = shilhouette + sh;
			}*/			 
		}

		return shilhouette / i;
	}
	
	

	/**
	 * Halla el clúster más cercano a pCluster
	 * @param pCluster
	 * @return
	 */
	private Cluster getClusterMasCercano(Cluster pCluster) {

		Iterator<Cluster> it = resultado.iterator();
		double min = Integer.MAX_VALUE;
		double dis = 0;
		Cluster resultado = null;

		while (it.hasNext()) {
			Cluster x = it.next();
			if (!x.equals(pCluster)) {
				dis = pCluster.getVector().getDistanceTo(x.getVector().getLista(), tipoDistancia);
				if (dis < min) {
					resultado = x;
					min = dis;
				}
			}
		}

		return resultado;
	}
	
	
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////               MÉTODOS                          /////////////////////////////////////////////////
///////////////////////////////              PARA CALCULAR                     /////////////////////////////////////////////////
///////////////////////////////              LAS VARIACIONES                   /////////////////////////////////////////////////
///////////////////////////////              DEL K-MEANS                       //////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Recalcula los Clusters en la variación 2K del K-Means
	 * @param pClusters
	 * @return
	 */
	public ArrayList<Cluster> recalcular2KClusters(ArrayList<Cluster> pClusters) {
		
		int filycol = pClusters.size();
		float[][] matriz = new float[filycol][filycol];
		int i = 0;
		int j = 0;
		ArrayList<Cluster> Clusters = new ArrayList<Cluster>();
		Iterator<Cluster> it = pClusters.iterator();
		Iterator<Cluster> it2 = pClusters.iterator();
		
		while (it.hasNext()) {
			
			Cluster c = it.next();
			
			while (it2.hasNext()) {
				
				// if(matriz[i][j]!=null)
				float dis = (float) c.getVector().getDistanceTo(it2.next().getVector().getLista(), tipoDistancia);
				matriz[i][j] = dis;
				// matriz[j][i] = dis;
				j = j + 1;
			}
			i = i + 1;
		}
		
		i = j = 0;
		int idef = 0;
		int jdef = 0;
		float disdef = 0;
		
		while (i <= filycol) {
			while (j <= filycol) {
				
				if (matriz[i][j] > disdef) {
					idef = i;
					jdef = j;
					disdef = matriz[i][j];
				}
				j++;
			}
			i++;
		}
		
		Clusters.add(pClusters.get(idef));
		Clusters.add(pClusters.get(jdef));
		return Clusters;
	}
	
	
	/**
	 * Asigna vectores iniciales en la variación 2k del K-Means
	 */
	public void asignarVector2kClusters() {
		// TODO Auto-generated method stub
		int i = 0;
		System.out.println("Valores de los centroides iniciales: ");
		while (i < 2 * k) {
			Cluster nuevo = new Cluster(getVectorAleatorio());
			if (!resultado.contains(nuevo)) {
				resultado.add(nuevo);
				nuevo.printCentroide();
				i++;
			}
		}
	}
	
	
	/**
	 * Asigna vectores en la variación de División del K-Means
	 */
	private void asignarVectorDivisionClusters() {
		// vamos a dividir las instancias en tantos grupos como clusters haya
		// el orden de la división será segun viene en el .arff
		// y escogeremos un vector aleatorio de cada grupo y se lo asignaremos a
		// cada cluster
		int i = 0;
		System.out.println("Valores de los centroides iniciales: ");
		while (i < k) {
			Cluster nuevo = new Cluster(getVectorAleatorioDivision(k, i));
			if (!resultado.contains(nuevo)) {
				resultado.add(nuevo);
				nuevo.printCentroide();
				i++;
			}
		}
	}

	
	/**
	 * Devuelve un vector aleatorio para la variación de División del K-Means
	 * @param k2
	 * @param i
	 * @return
	 */
	private Instancia getVectorAleatorioDivision(int k2, int i) {
		// TODO Auto-generated method stub
		return DataBase.getDataBase().getRandomVectorDivision(k2, i);
	}
	

	private Cluster getClusterMasDistante(Cluster pCluster) {
		Iterator<Cluster> it = resultado.iterator();
		ArrayList<Double> distancias = new ArrayList<Double>();
		ArrayList<Cluster> clusters = new ArrayList<Cluster>();
		Cluster resultado = null;
		int i = 0;
		Double comp;
		while (it.hasNext()) {
			Cluster x = it.next();
			if (!x.equals(pCluster)) {
				distancias.add(pCluster.getVector().getDistanceTo(x.getVector().getLista(), tipoDistancia));
				clusters.add(x);
			}
		}
		comp = distancias.get(0);
		for (Double dis : distancias) {
			if (comp <= dis) {
				comp = dis;
				resultado = clusters.get(i);
			}
			i = i + 1;
		}
		return resultado;
	}

	

	
	

}