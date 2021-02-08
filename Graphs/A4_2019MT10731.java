import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;

class Vertex
{
    public int id;
    public int weight;
    public String name;

    public Vertex() {
        
    }
    public Vertex(int id, String name) {
        this.id = id;
        this.name=name;
        this.weight = 0;
    }

    public int getId() {
        return id;
    }
}
class Graph {
public int V,E; 
 

    public Graph(int v, int e) {
        this.V = v;
        this.E = e;
        // this.adj = new LinkedList[v];
        // for(int i=0; i<v; i++)
        //     this.adj[i] = new LinkedList<>();
    }

    public Graph(){

    }

    public static void addEdge(ArrayList<ArrayList<Integer> > adj, int u, int v)
    {
        adj.get(u).add(v);
        adj.get(v).add(u);
        
    }
  
} 

public class A4_2019MT10731{

    public static void average(int m, int n){
        double value = ((float)(2*m))/n;
        value = Math.round(value*100.0)/100.0;
        System.out.printf("%.2f", value);
        System.out.println();
    }
    

    public static void rank(int n, ArrayList<Vertex> str){

        
        sort(str,0,n-1);
        if (str.size() >= 1) {
            System.out.print(str.get(0).name);
        }

        for (int i = 1; i < str.size(); i++) { 
             System.out.print("," + str.get(i).name);
        }
        System.out.println();


    }

    public static void merge(ArrayList<Vertex> str, int l, int m, int r)
    {
        int n1 = m - l + 1;
        int n2 = r - m;
 
        Vertex L[] = new Vertex[n1];
        Vertex R[] = new Vertex[n2];
        
        for (int i = 0; i < n1; i++){
            L[i] = str.get(l+i); 
        }

        for (int j = 0; j < n2; j++){
            R[j] = str.get(m + 1 + j);
        }
 
        int i = 0, j = 0;
 
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i].id < R[j].id) {
                str.set(k, R[j]);
                j++;
                
            }
            else if (L[i].id > R[j].id) {
                str.set(k, L[i]);
                i++;
                
            }
            else{
                String s1 = (L[i]).name; 
                String s2 = (R[j]).name;
                int var1 = s1.compareTo(s2);
                if(var1>=0){
                    str.set(k, L[i]);
                    i++;
                }
                else {
                    str.set(k, R[j]);

                    j++;
                }
            }
            k++;
        }
 
        while (i < n1) {
            str.set(k, L[i]);

            i++;
            k++;
        }
 
        while (j < n2) {
            str.set(k, R[j]);
            j++;
            k++;
        }
    }
 

    public static void sort(ArrayList<Vertex> str, int l, int r)
    {
        if (l < r) {
            
            int m = (l + r) / 2;
 
            sort( str, l, m);
            sort( str, m + 1, r);
 
            merge(str, l, m, r);
        }
        
    }

    public static void DFSUtil(int v, boolean visited[], ArrayList<ArrayList<Integer> > grap, ArrayList<Vertex> fsort,ArrayList<Vertex> str,ArrayList<String> listofnames)
    {   
        
        visited[v] = true;
        
        listofnames.add(str.get(v).name);
        Iterator<Integer> i = grap.get(v).listIterator();
        while (i.hasNext()) 
        {
            int n = i.next();
            if (!visited[n])
                DFSUtil(n, visited,grap,fsort,str,listofnames);
        }

    }
 
    public static void DFS(int v, ArrayList<ArrayList<Integer> > grap, boolean visited[],ArrayList<Vertex> str )
    {
        ArrayList<Vertex> fsort = new ArrayList<Vertex>();
        ArrayList<String> listofnames = new ArrayList<String>();

        
        
        for(int j=0; j<v; j++){
            if(!visited[j]){
                
                DFSUtil(j,visited,grap,fsort,str,listofnames);
                int m = listofnames.size();
                for (int p = 0; p < listofnames.size(); p++) { 
                    for (int q = p + 1; q < listofnames.size(); q++) { 
                        if (listofnames.get(p).compareToIgnoreCase(listofnames.get(q)) < 0) { 
                            String temp = listofnames.get(p); 
                            listofnames.set(p,listofnames.get(q));
                            listofnames.set(q,temp);
                          
                        } 
                    } 
                } 
                String toPrint = "";
                for(int p=0; p<listofnames.size()-1; p++){
                  toPrint += listofnames.get(p)+",";
                }
                toPrint += listofnames.get(listofnames.size()-1);
                listofnames = new ArrayList<String>();
                fsort.add(new Vertex(m,toPrint));
                toPrint = null;

            }
        }
        int x = fsort.size();
        sort(fsort,0,x-1);
        for (int i = 0; i < x; i++) { 
             System.out.println(fsort.get(i).name);
        }
        
    }

    public static void independent_storylines_dfs(int i, ArrayList<ArrayList<Integer> > grap,ArrayList<Vertex> str){
        
        boolean visited[] = new boolean[i];
        DFS(i, grap,visited,str);
    }






    public static void main(String[] args){


        String line = "";
        ArrayList<Vertex> str = new ArrayList<Vertex>();
        Map<String, Integer> identity = new HashMap<>();
        Map<String, LinkedList<String>> graph = new HashMap<>();
        try   
        {   
            BufferedReader br = new BufferedReader(new FileReader(args[0])); 
            line = br.readLine();
            int i = 0;
            while ((line = br.readLine()) != null){  
                
                    String[] splits = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                     if(splits[1].charAt(0) == '"'){
                        splits[1] = splits[1].substring(1,splits[1].length()-1);
                     }
                    str.add(new Vertex(i,splits[1]));   
                    identity.put(splits[1],i);
                    i=i+1;          
            } 

            ArrayList<ArrayList<Integer> > grap = new ArrayList<>(i);
            for (int t = 0; t<i; t++){
                grap.add(new ArrayList<>());
            }

            Graph g = new Graph();
            
     
            BufferedReader brr = new BufferedReader(new FileReader(args[1])); 
            line = brr.readLine();
            int m = 0;
            while ((line = brr.readLine()) != null){  
                
                    String[] splits = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                     if(splits[1].charAt(0) == '"'){
                        splits[1] = splits[1].substring(1,splits[1].length()-1);
                     }
                     if(splits[0].charAt(0) == '"'){
                        splits[0] = splits[0].substring(1,splits[0].length()-1);
                     }
                    String firstNode = splits[0];
                    String secondNode = splits[1];  
                    int w = Integer.parseInt(splits[2]);

                    int i1 = identity.get(firstNode);
                    int i2 = identity.get(secondNode);

                    if(w>=0) str.get(i1).weight+=w;
                    if(w>=0) str.get(i2).weight+=w;
                    m=m+1;

                    g.addEdge(grap, i1 , i2);
            }

            


      
            String func = (args[2]); 
            if(func.compareTo("average")==0){
                average(m,i);
            } 

            else if(func.compareTo("rank")==0){
                rank(i, str);
            }
            

            else if(func.compareTo("independent_storylines_dfs")==0){
                
                independent_storylines_dfs(i,grap,str);
            }

        }   
        catch (IOException e){  
            e.printStackTrace();  
        } 
    }


}