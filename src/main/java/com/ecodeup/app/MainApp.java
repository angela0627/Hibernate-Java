package com.ecodeup.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.ecodeup.model.Producto;

public class MainApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int opcion = 0;
		Scanner scanner = new Scanner(System.in);
		Producto producto;

		EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();
		while (opcion!=5) {
			System.out.println("1. Crear Macota");
			System.out.println("2. Buscar Macota");
			System.out.println("3. Actualizar Macota");
			System.out.println("4. Eliminar Macota");
			System.out.println("5. Salir");
			System.out.println("Elija una opción:");

			opcion = scanner.nextInt();
			switch (opcion) {
			case 1:
				System.out.println("Digite el nombre de la macota:");
				producto = new Producto();
				producto.setId(null);
				scanner.nextLine();
				producto.setNombre(scanner.nextLine());

				System.out.println("Digite el precio de la mascota");
				producto.setPrecio(scanner.nextDouble());
				System.out.println(producto);
				entity.getTransaction().begin();
				entity.persist(producto);
				entity.getTransaction().commit();
				System.out.println("Mascota  registrada..");
				System.out.println();
				break;

			case 2:
				System.out.println("Digite el id de la mascota a buscar:");
				producto = new Producto();
				producto = entity.find(Producto.class, scanner.nextLong());
				if (producto != null) {
					System.out.println(producto);
					System.out.println();
				} else {
					System.out.println();
					System.out.println("Macota no encontrado... Lista de mascotas completa");
					List<Producto> listaProductos= new ArrayList<>();
					Query query=entity.createQuery("SELECT p FROM Producto p");
					listaProductos=query.getResultList();
					for (Producto p : listaProductos) {
						System.out.println(p);
					}
					
					System.out.println();
				}

				break;
			case 3:
				System.out.println("Digite el id de la mascota a actualizar:");
				producto = new Producto();

				producto = entity.find(Producto.class, scanner.nextLong());
				if (producto != null) {
					System.out.println(producto);
					System.out.println("Digite el nombre de la mascota:");
					scanner.nextLine();
					producto.setNombre(scanner.nextLine());
					System.out.println("Digite el precio de la mascota:");
					producto.setPrecio(scanner.nextDouble());
					entity.getTransaction().begin();
					entity.merge(producto);
					entity.getTransaction().commit();
					System.out.println("Macota actualizada..");
					System.out.println();
				} else {
					System.out.println("Mascota no encontrada....");
					System.out.println();
				}
				break;
			case 4:
				System.out.println("Digite el id de la mascota a eliminar:");
				producto = new Producto();

				producto = entity.find(Producto.class, scanner.nextLong());
				if (producto != null) {
					System.out.println(producto);
					entity.getTransaction().begin();
					entity.remove(producto);
					entity.getTransaction().commit();
					System.out.println("Macota eliminada...");
				} else {
					System.out.println("Macota no encontrada...");
				}
				break;
			case 5:entity.close();JPAUtil.shutdown();
			break;

			default:
				System.out.println("Opción no válida\n");
				break;
			}
		}
	}

}