package model.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import controller.Controller;
import model.data_structures.ArregloDinamico;
import model.data_structures.Lista;
import model.data_structures.ListaEncadenada;


/**
 * Definicion del modelo del mundo
 *
 */
public class Cinema {
	/**
	 * Atributos del modelo del mundo
	 */
	private Lista datos;

	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public static final String SEPARATOR=";";

	private ArregloDinamico<Pelicula> peliculas; 

	private ArregloDinamico<Director> directores;

	private ArregloDinamico<Genero> generos;

	private ListaEncadenada<Pelicula> lasPeliculas;

	private ListaEncadenada<Director> losDirectores;
	
	private ArregloDinamico<Actor> actor;

	public Cinema(int tamano )
	{
		peliculas = new ArregloDinamico<Pelicula>(tamano);
		directores = new  ArregloDinamico<Director> (100);
		generos = new ArregloDinamico<Genero>(176);
		lasPeliculas = new ListaEncadenada<Pelicula>();
		actor = new ArregloDinamico<>(50);
	}
	public ArregloDinamico<Pelicula> darPeliculas()
	{
		return peliculas;
	}

	public ListaEncadenada<Pelicula>darPeliculasLista(){
		return lasPeliculas;
	}

	/**
	 * Servicio de consulta de numero de elementos presentes en el modelo 
	 * @return numero de elementos presentes en el modelo
	 */
	public int darTamano()
	{
		return peliculas.size();
	}

	/**
	 * Requerimiento de agregar dato
	 * @param dato
	 */
	public void agregar(Pelicula dato)
	{	
		peliculas.agregar(dato);
	}

	/**
	 * Requerimiento buscar dato
	 * @param dato Dato a buscar
	 * @return dato encontrado
	 */
	public Comparable buscar(int pos)
	{
		return  peliculas.getElement(pos);
	}

	/**
	 * Requerimiento eliminar dato
	 * @param dato Dato a eliminar
	 * @return dato eliminado
	 */
	public Comparable eliminar(int pos)
	{
		return peliculas.deleteElement(pos);
	}




	public Director buscarDirector (String p)
	{
		Director buscado = null;

		for(int i=0; i < directores.size(); i++){
			Director actual = directores.darElemento(i);
			if( p.equals(actual.darDirector())){
				buscado = actual;
			}
		}
		return buscado;
	}
	public Genero buscarGenero(String g)
	{
		Genero buscado = null;

		for(int i=0; i < generos.size(); i++){
			Genero actual = generos.darElemento(i);
			if( g.equals(actual.darNombre())){
				buscado = actual;
			}
		}
		return buscado;
	}


	public void agregarPeliculaDirector(Director d, Pelicula p)
	{

		for(int i=0; i < directores.size(); i++){
			Director actual = directores.darElemento(i);
			if( d.darDirector().equals(actual.darDirector())){
				actual.agregarPelicula(p);
			}	
		}

	}



	public ArregloDinamico<Pelicula> conocerUnDirector (String p)
	{
		ArregloDinamico respuesta = new ArregloDinamico(10);
		for(int i = 0; i<directores.size();i++){
			if(buscarDirector(p)!= null){
				respuesta = buscarDirector(p).darPeliculas();
				return  respuesta;
			}
		}
		return  respuesta;

	}


	public ArregloDinamico<Pelicula> darBuenasPeliculas(String director)
	{

		ArregloDinamico<Pelicula> buenas = new ArregloDinamico<Pelicula>(50);
		for(int i = 0; i < peliculas.size(); i++)
		{

			Pelicula act = peliculas.getElement(i);
			if(act.darCasting().directorName().equals(director) && act.darVote_average() >=6.0)
				buenas.addLast(act);

		}
		return buenas;
	}

	public ArregloDinamico<Pelicula> darRankingPeliculasVotos()
	{
		ArregloDinamico<Pelicula> top = new ArregloDinamico<Pelicula>(20);
		for(int i = 0; i < peliculas.size(); i++)
		{

			Pelicula r = peliculas.getElement(i);
			if(r.darVote_average() >= 8.0)
			{
				top.addLast(r);
			}
		}
		for(int i = 0; i < top.size(); i++)
		{
			Pelicula primera = top.darElemento(i);
			for(int j = i + 1; j < top.size() ; i++)
			{
				Pelicula comparar = top.darElemento(j);
				if(comparar.darVote_average() >= primera.darVote_average())
				{
					top.changeInfo(i, top.darElemento(j)); 
					top.changeInfo(j, primera);
				}
			}
		}
		return top;
	}
	
	public void agregarAlGenero(String nuevo, Pelicula nPel)
	{
		int a = -1;
		for(int i = 0; i < generos.size(); i++)
		{
			if(generos.getElement(i).darNombre().equals(nuevo))
				a = i;
		}
		if(a == -1)
		{
			Genero n = new Genero(nuevo);
			generos.addLast(n);
			n.agregarPelicula(nPel);
		}
		else
			generos.getElement(a).agregarPelicula(nPel);
	}
	
	public String entenderUnGenero(String genero)
	{
		Genero busq = null; 
		for(int i = 0; i < generos.size() && busq == null; i++)
		{
			Genero act = generos.getElement(i);
			busq = act.darNombre().equals(genero) ? act : null;
		}
		return busq.toString();
	}
	
	public ListaEncadenada<Pelicula> toListaEncadenda(Comparable[] arreglo)
	{
		ListaEncadenada<Pelicula> nueva = new ListaEncadenada<Pelicula>();
		for(Comparable act:arreglo)
			nueva.addLast((Pelicula) act);
		return nueva;
	}
	
	public ArregloDinamico<Pelicula> toArregloDinamico(Comparable[] arreglo)
	{
		ArregloDinamico<Pelicula> nueva = new ArregloDinamico<Pelicula>(500);
		for(Comparable act:arreglo)
			nueva.addLast((Pelicula) act);
		return nueva;
	}
	
	public ListaEncadenada<Pelicula> ordenarPeoresVA(ArregloDinamico<Pelicula> pels, int tot)
	{
		ListaEncadenada<Pelicula> resp = toListaEncadenda(Sorts.miniSort((Comparable[]) pels.darElementos(), tot));
		double margen = resp.lastElement().darVote_average();
		int tam = resp.size();
		for(int i = tot; i < pels.size(); i ++)
		{
			Pelicula act = pels.getElement(i);
			if(act.darVote_average() < margen)
			{
				int j = 0;
				while(act.darVote_average() > resp.getElement(j).darVote_average() && j < tam)
					j++;
				resp.changeInfo(j, act);
				margen = resp.lastElement().darVote_average();
			}
		}
		return resp;
	}
	public ListaEncadenada<Pelicula> ordenarMejoresVA(ArregloDinamico<Pelicula> pels, int tot)
	{
		ListaEncadenada<Pelicula> resp = toListaEncadenda(Sorts.miniSort((Comparable[]) pels.darElementos(), tot));
		double margen = resp.firstElement().darVote_average();
		int tam = resp.size();
		for(int i = tot; i < pels.size(); i ++)
		{
			Pelicula act = pels.getElement(i);
			if(act.darVote_average() > margen)
			{
				int j = resp.size() -1;
				while(act.darVote_average() < resp.getElement(j).darVote_average() && j >= 0)
					j--;
				resp.changeInfo(j, act);
				margen = resp.firstElement().darVote_average();
			}
		}
		return resp;
	}
	
	public ListaEncadenada<Pelicula> ordenarPeoresVC(ArregloDinamico<Pelicula> pels, int tot)
	{
		ListaEncadenada<Pelicula> resp = toListaEncadenda(Sorts.miniSort((Comparable[]) pels.darElementos(), tot));
		double margen = resp.lastElement().darVote_count();
		int tam = resp.size();
		for(int i = tot; i < pels.size(); i++)
		{
			Pelicula act = pels.getElement(i);
			if(act.darVote_count() < margen)
			{
				int j = 0;
				while(act.darVote_count() > resp.getElement(j).darVote_count() && j + 1 < tam)
					j++;
				resp.changeInfo(j, act);
				margen = resp.lastElement().darVote_count();
			}
		}
		return resp;
	}
	public ArregloDinamico<Pelicula> ordenarMejoresVC(ArregloDinamico<Pelicula> pels, int tot)
	{
		ArregloDinamico<Pelicula> resp = toArregloDinamico(Sorts.miniSort((Comparable[]) pels.darElementos(), tot));
		double margen = resp.firstElement().darVote_count();
		for(int i = tot; i < pels.size(); i ++)
		{
			Pelicula act = pels.getElement(i);
			if(act.darVote_count() > margen)
			{
				int j = resp.size() -1;
				while(act.darVote_count() < resp.getElement(j).darVote_count() && j >= 0)
					j--;
				resp.changeInfo(j, act);
				margen = resp.firstElement().darVote_count();
			}
		}
		return resp;
	}
	
	public Lista<Pelicula> darRankPeliculas(int num, int forma)
	{
		Lista<Pelicula> result = null;
		if(forma == 0)
			result = ordenarPeoresVA(peliculas, num);
		
		else if(forma == 1)
			result = ordenarMejoresVA(peliculas, num);
		
		else if(forma == 2)
			result = ordenarPeoresVC(peliculas, num);
		
		else if (forma == 3)
			result = ordenarMejoresVC(peliculas, num);
		
		return result;
	}
	
	public Lista<Pelicula> darRankGenero(int num, int forma, String genero)
	{
		Genero temp = buscarGenero(genero);
		ArregloDinamico<Pelicula> arr = temp.darPeliculas();
		Lista<Pelicula> result = null;
		if(forma == 0)
			result = ordenarPeoresVA(arr, num);
		
		else if(forma == 1)
			result = ordenarMejoresVA(arr, num);
		
		else if(forma == 2)
			result = ordenarPeoresVC(arr, num);
		
		else if(forma == 3)
			result = ordenarMejoresVC(arr, num);
		
		return result;
	}


	public ArregloDinamico<Pelicula> darRankingPeliculasPromedio()
	{
		ArregloDinamico<Pelicula> top = new ArregloDinamico<Pelicula>(20);
		for(int i = 0; i < peliculas.size(); i++)
		{

			Pelicula r = peliculas.getElement(i);
			if(r.darVote_count() >= 2500)
			{
				top.addLast(r);
			}
		}
		for(int i = 0; i < top.size(); i++)
		{
			Pelicula primera = top.darElemento(i);
			for(int j = i + 1; j < top.size() ; i++)
			{
				Pelicula comparar = top.darElemento(j);
				if(comparar.darVote_count() >= primera.darVote_count())
				{
					top.changeInfo(i, top.darElemento(j)); 
					top.changeInfo(j, primera);
				}
			}
		}	
		return top;
	}
	
	public ArregloDinamico<Actor> darActor (String nNombre)
	{
		ArregloDinamico<Actor> r = new ArregloDinamico<Actor>(1000);
		for(int i=0; i < actor.size(); i++)
		{
			Actor pos = actor.darElemento(i);
			if(pos.darActor().equals(nNombre))
			{
				r.addFirst(pos);
			}
		}
		return r;
	}
	
	public void agregarAlActor(String nActor, Pelicula nPel)
	{
		int a = -1;
		for(int i = 0; i < actor.size(); i++)
		{
			if(actor.getElement(i).darActor().equals(nActor))
				a = i;
		}
		if(a == -1)
		{
			Actor n = new Actor(nActor);
			actor.addLast(n);
			n.agregarPelicula(nPel);
		}
		else
			actor.getElement(a).agregarPelicula(nPel);
	}
	public void CargarArchivosArreglo()
	{

		BufferedReader bufferLectura = null;

		try{
			bufferLectura = new BufferedReader(new FileReader("./data/SmallMoviesDetailsCleaned.csv"));

			String linea = bufferLectura.readLine();
			linea = bufferLectura.readLine();
			while (linea!= null)
			{
				
				String[] campos = linea.split(SEPARATOR);
				Pelicula temp = new Pelicula(Integer.parseInt(campos[0].trim()), campos[1], campos[2], campos[3], campos[4], campos[5], campos[6], campos[7], campos[8], campos[9], campos[10], campos[11], campos[12], campos[13], campos[14], campos[15], campos[16], Double.parseDouble(campos[17].trim()), Double.parseDouble(campos[18].trim()), campos[19], campos[20], null);
				peliculas.agregar(temp);
				linea = bufferLectura.readLine();
			}
			bufferLectura.close();

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}


		try{
			bufferLectura = new BufferedReader(new FileReader("./data\\MoviesCastingRaw-small.csv"));

			String linea = bufferLectura.readLine();
			linea = bufferLectura.readLine();
			int i = 0;
			while (linea!= null)
			{
				String[] campos = linea.split(SEPARATOR);

				Casting temp = new Casting (Integer.parseInt(campos[0]), campos[1],Integer.parseInt(campos[2]), campos[3], Integer.parseInt(campos[4]), campos[5], Integer.parseInt(campos[6]), campos[7], Integer.parseInt(campos[8]), campos[9], Integer.parseInt(campos[10]), Integer.parseInt(campos[11]), campos[12].trim(), Integer.parseInt(campos[13]), Integer.parseInt(campos[14]), campos[15], Integer.parseInt(campos[16]), campos[17], campos[18]);
				Director tempo= new Director(Integer.parseInt(campos[0]), campos[12].trim());
				Pelicula actual = peliculas.getElement(i);
				String[] a = actual.darGeneros().trim().split("\\|");
				for(String genTemp: a)
					agregarAlGenero(genTemp, actual);
				

				actual.cambiarCast(temp);
				agregarPeliculaDirector(tempo, actual);
				i++;
				linea = bufferLectura.readLine();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			if( bufferLectura != null)
			{
				try
				{
					bufferLectura.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public void CargarArchivosLista()
	{

		BufferedReader bufferLectura = null;

		try{
			bufferLectura = new BufferedReader(new FileReader("./data/SmallMoviesDetailsCleaned.csv"));

			String linea = bufferLectura.readLine();
			linea = bufferLectura.readLine();
			while (linea!= null)
			{
				String[] campos = linea.split(SEPARATOR);
				Pelicula temp = new Pelicula(Integer.parseInt(campos[0].trim()), campos[1], campos[2], campos[3], campos[4], campos[5], campos[6], campos[7], campos[8], campos[9], campos[10], campos[11], campos[12], campos[13], campos[14], campos[15], campos[16], Double.parseDouble(campos[17].trim()), Double.parseDouble(campos[18].trim()), campos[19], campos[20], null);
				lasPeliculas.addLast(temp);
				linea = bufferLectura.readLine();
			}
			bufferLectura.close();

		}
		catch(IOException e)
		{
			e.printStackTrace();
		}


		try{
			bufferLectura = new BufferedReader(new FileReader("./data\\MoviesCastingRaw-small.csv"));

			String linea = bufferLectura.readLine();
			linea = bufferLectura.readLine();
			int i = 0;
			while (linea!= null)
			{
				String[] campos = linea.split(SEPARATOR);

				Casting temp = new Casting (Integer.parseInt(campos[0]), campos[1],Integer.parseInt(campos[2]), campos[3], Integer.parseInt(campos[4]), campos[5], Integer.parseInt(campos[6]), campos[7], Integer.parseInt(campos[8]), campos[9], Integer.parseInt(campos[10]), Integer.parseInt(campos[11]), campos[12].trim(), Integer.parseInt(campos[13]), Integer.parseInt(campos[14]), campos[15], Integer.parseInt(campos[16]), campos[17], campos[18]);
				lasPeliculas.getElement(i).cambiarCast(temp);
				i++;
				linea = bufferLectura.readLine();
			}
		}

		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			if( bufferLectura != null)
			{
				try
				{
					bufferLectura.close();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

}
