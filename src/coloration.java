import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class coloration {
    int vertex; // nb de sommet
    int colorMax; // nb de colorMax
    boolean flag = false; // si on peut stoper
    int[][] matrix; // matrix adjacence
    int[] colorlist; // liste couleur
    int[] colorcomparing;
    int k;


    coloration(int vertex,int colorMax) {
        this.vertex = vertex;
        this.colorMax = colorMax;
        this.matrix = new int[vertex][vertex];
        this.colorlist = new int[vertex];
        this.colorcomparing = new int[vertex];
        this.k = 1;
    }

    public void addEdge(int source, int destination) { //matrice adjacence
        matrix[source][destination] = 1;
        matrix[destination][source] = 1;
    }

    public void run()
    {
        findColoring(k);
        if(flag) {
            for (int i = 1; i < vertex; i++) {
                System.out.println("Le sommet :" + i + " --> " + "couleur " + colorcomparing[i] + "\n");
            }
        }

    }

    public boolean compare(int k)
            /*
            méthode qui compare le sommet dans la matrice adjacence
            et qui vérifie grâce aux indice , si les sommets qui sont en conflit
            on différente couleur
             */
    {
        for(int j=0;j<vertex;j++) // o(n)
        {
            if(matrix[k][j]==1 && colorlist[j]==colorlist[k])
            {
                return false;
            }
        }
        return true;
    }


    public void findColoring(int k)
            /*
            Pour éviter de chercher toute les possibilités de couleur différentes ,
            la méthode backtrack vérifie les couleurs et fait un parcours en ...

            ce qu'on fait ici c'est qu'on parcours la matrice d'adjacence pour voir les différents sommet
            relier entre eux ,
            ici la méthode backtrack permet de vérifier si un sommet peut avoir 1 jusqu'à colorMax
            si la coloration ne correspond pas on backtrack en changeant la couleur du k initial précédent
            et on reparcours de nouveau jusqu'à tomber sur le graphe coloré
             */
    {
        if(k==vertex)
        {
            int count = 0;
            for (int i = 0; i < vertex; i++) {//o(n)
                if(colorlist[i] > count)
                {
                    count = colorlist[i];
                }
            }
            if(count <= colorMax)
            {
                System.arraycopy(colorlist,0,colorcomparing,0,colorlist.length);
                colorMax = count;
            }
            flag = true;
            return; // une fois le return executer le restant du code ne sera plus executer

        }
        for(int j=1;j<=colorMax;j++)//o(k)
        {
            colorlist[k] = j;
            if(compare(k))//o(n)
            {
                findColoring(k+1); // o(k^n)
            }
        }
        colorlist[k] = 0;
    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(args[0]);
        Scanner fichier = new Scanner(file);
        coloration Graphe = null;
        while(fichier.hasNextLine()){
            String data = fichier.nextLine();
            String[] Graph = data.split(" ");
            if( Graph.length == 1){
                Graphe = new coloration(Integer.parseInt(Graph[0])+1,Integer.parseInt(args[1]));
            }
            else
            {
                assert Graphe != null;
                Graphe.addEdge(Integer.parseInt(Graph[0]),Integer.parseInt(Graph[1]));
            }
        }
        assert Graphe != null;
        Graphe.run();
    }
}
