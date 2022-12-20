import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class ordonnancement {
    int vertex; // sommet
    int colorMax; // couleur max
    boolean flag = false; // boolean servant à print si le retour est bon
    float[][] matrix; // matrice qui reçoit les différentes tâches
    int[][] matrixAdj; // matrice d'adjacence

    ordonnancement(int vertex, int colorMax) // constructeur qui reçoit en paramètre les sommet et la couleur max
    {
        this.vertex = vertex;
        this.colorMax = colorMax;
        this.matrix = new float[vertex][2];
        this.matrixAdj = new int[vertex][vertex];
    }

    public void addEd(int source,float first,float second)
    {
        matrix[source][0] = first;
        matrix[source][1] = second;
    }

    public void addMatrixAdj()
    {

        Arrays.sort(matrix, new Comparator<float[]>() {// o(n log n ) utilisation de mergesort
            @Override
            public int compare(float[] o1, float[] o2) {
                return Float.compare(o1[0],o2[0]);
            }
        });
        for(int i=1;i< vertex-1;i++) // o(n-1)
        {
            for(int j=i+1;j<vertex;j++) // o(n)
            {
                if(matrix[j][1] <= matrix[i][0] || matrix[i][1] <= matrix[j][0])
                {
                    assert true;
                }
                else
                {
                    addMatrix(i,j);
                }
            }
        }
    }

    public void addMatrix(int source,int destination)
    {
        matrixAdj[source][destination] = 1;
        matrixAdj[destination][source] = 1;
    }

    public boolean compare(int[] array,int couleur)
    {
        /*
        Si la couleur est en conflit avec la liste
         */
        for(int i=0;i<vertex;i++){
            if (couleur == array[i])
                return false;
        }
        return true;
    }

    public boolean compareColor(int couleur)
    {
        if(couleur > colorMax) {
            this.flag = true;
            return false;
        }
        return true;
    }

    public void printGraphColor(int[] array)
    {
        for(int i=1;i<vertex;i++)
        {
            System.out.println("La tache :" + i + " --> " + "machine " + array[i]);
        }
    }

    public void findColoring_ord() { // o(n^2*k)
        /*
        une liste result qui permet de stocker les couleurs trouver
        une liste res qui permet de stocker les couleurs en conflit dans la matrice d'adjacence
        on parcours chaque sommet et on lui donne une couleur , si ce sommet est en conflit avec un autre alors on change
        de couleur jusqu'à trouver la bonne couleur , si la couleur dépasse le nombre k qui est le nombre de couleurMax
        Alors on arrête la boucle.
         */
        int color;
        int[] result = new int[vertex];
        int[] res = new int[vertex];
        Arrays.fill(result, -1);

        result[1] = 1; // on colorie le premier
        for (int i = 1; i < vertex; i++) { // o(n)
            Arrays.fill(res, -1);
            color = 1;
            if (i != 1) {//o(1)
                for (int k = 1; k < vertex; k++) { //o(n)
                    if (matrixAdj[i][k] == 1) { // if true //o(1)
                        if (result[k] != -1) { // if couleur //o(1)
                            res[k] = result[k];
                        }
                    }
                }
                while(!compare(res,color)){ //o(n*k) k is color
                    color++;
                    if(!compareColor(color))//o(1)
                        break;
                }
                if(!compareColor(color))//o(1)
                    break;
                result[i] = color;

            }
        }
        if(!flag)
        {
            printGraphColor(result);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(args[0]);
        Scanner fichier = new Scanner(file);
        ordonnancement ord = null;
        while (fichier.hasNextLine()) {
            String buffer = fichier.nextLine();
            String[] Graphe = buffer.split(" ");
            if( Graphe.length == 1){
                ord = new ordonnancement(Integer.parseInt(Graphe[0])+1,Integer.parseInt(args[1]));
            }
            else
            {
                assert ord != null;
                ord.addEd(Integer.parseInt(Graphe[0]),Float.parseFloat(Graphe[1]),Float.parseFloat(Graphe[2]));
            }
        }
        assert ord != null;
        ord.addMatrixAdj();
        ord.findColoring_ord();
    }
}
