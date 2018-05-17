/*
 * This class represents the Command Line Interface, used to interact with the user.
 */
package main.java.com.excilys.computerdatabase.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import main.java.com.excilys.computerdatabase.model.Company;
import main.java.com.excilys.computerdatabase.model.Computer;

@Component
public class CLI {

    private static Scanner scan;

    /**
     * Inits the view by enabling user interaction.
     */
    public void initView() {
        scan = new Scanner(System.in);
    }

    /**
     * Closes interactions with the user.
     */
    public void stop() {
        scan.close();
    }

    /**
     * Shows all the actions available.
     */
    public void showActions() {
        System.out.println();
        System.out.println("---------------------------------------------------------------");
        System.out.println("-------------- Liste des commandes disponibles : --------------");
        System.out.println("---------------------------------------------------------------");
        System.out.println("0 - Quitter");
        System.out.println("1 - Obtenir la liste de tous les ordinateurs");
        System.out.println("2 - Obtenir les informations d'un ordinateur à partir de son id");
        System.out.println("3 - Supprimer un ordinateur à partir de son id");
        System.out.println("4 - Mettre à jour les informations d'un ordinateur");
        System.out.println("5 - Créer un nouvel ordinateur");
        System.out.println("6 - Voir la liste des entreprises");
        System.out.println("7 - Supprimer une entreprise");
        System.out.println("---------------------------------------------------------------");
    }

    /**
     * Prints the computer data.
     * @param cpu the computer
     */
    public void printComputer(Computer cpu) {
        System.out.println(cpu);
    }

    /**
     * Template list printing.
     * @param <T> the generic type
     * @param list the list
     */
    public <T> void printList(List<T> list) {
        for (T item : list) {
            System.out.println(item);
        }
    }

    /**
     * Prints the keys indication to navigate through the pages.
     */
    public void printPageNavigationIndication() {
        System.out.println();
        System.out.println("m : retour au menu");
        System.out.println("s : page suivante");
        System.out.println("p : page précédente");
        System.out.println();
    }

    /**
     * Reads user input.
     * @return the string
     */
    public String readData() {
        System.out.print("> ");
        return scan.nextLine();
    }

    /**
     * Reads a long.
     * @return the long
     */
    public Long readLong() {
        try {
            return Long.parseLong(readData());
        } catch (Exception e) {
            System.out.println("Entrez un nombre entier svp.");
            return readLong();
        }
    }
    
    /**
     * Reads an integer.
     * @return the int
     */
    public int readInt() {
        try {
            return Integer.parseInt(readData());
        } catch (Exception e) {
            System.out.println("Entrez un nombre entier svp.");
            return readInt();
        }
    }

    /**
     * Reads a string.
     * @return the string
     */
    public String readString() {
        return readData();
    }

    /**
     * Reads a date.
     * @return the date
     */
    public LocalDate readDate() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            return LocalDate.parse(readString(), format);
        } catch (DateTimeParseException e) {
            System.out.println("Erreur dans le format de la date, veuillez réessayer : dd/MM/yyyy");
            return readDate();
        }
    }

    /**
     * Reads computer data to update.
     * @return the computer
     */
    public Computer readCpuToUpdate() {
        System.out.println("ID de l'ordinateur ?");
        Long id = readLong();
        System.out.println("Nom de l'ordinateur ?");
        String name = readString();
        System.out.println("Date de mise sur le marché ?");
        LocalDate introduced = readDate();
        System.out.println("Date de retrait du marché ?");
        LocalDate discontinued = readDate();
        System.out.println("ID de l'entreprise ?");
        Long compID = readLong();
        Company company = new Company.Builder(compID).build();
        return new Computer.Builder(name).withId(id).withCompany(company).withIntroduced(introduced)
                .withDiscontinued(discontinued).build();
    }

    /**
     * Reads computer to create.
     * @return the computer
     */
    public Computer readCpuToCreate() {
        System.out.println("Nom de l'ordinateur ?");
        String name = readString();
        System.out.println("Date de mise sur le marché ?");
        LocalDate introduced = readDate();
        System.out.println("Date de retrait du marché ?");
        LocalDate discontinued = readDate();
        System.out.println("ID de l'entreprise ?");
        Long compID = readLong();
        Company company = new Company.Builder(compID).build();
        return new Computer.Builder(name).withCompany(company).withIntroduced(introduced).withDiscontinued(discontinued)
                .build();
    }

}
