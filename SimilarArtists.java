import java.util.*;
import java.io.*;

public class SimilarArtists {

    HashMap<Artist, Edge> artistMap;

    Artist start, end;

    Graph graph;

    Stack<Artist> currentPath;

    HashSet<Artist> visited;

    public SimilarArtists(){

        artistMap = new HashMap<>();

        graph = new Graph();

        File file = new File("SimilarArtists.txt");

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] info = line.split(", ");

                Artist artist1 = new Artist(info[0]);
                Artist artist2 = new Artist(info[1]);


                graph.addArtist(artist1);
                graph.addArtist(artist2);

                graph.addEdge(artist1, artist2);
                graph.addEdge(artist2, artist1);


                
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("Edges - Connecting artists with similar");
        for(Edge e : graph.getEdges()) {
            System.out.println("\t" + e);
        }


        for (Artist startingArtist : graph.getArtists()) {
            System.out.println(startingArtist);
            
            for(Artist endingArtist : graph.getArtists()) {

                if (startingArtist == endingArtist) continue;

                currentPath = new Stack<Artist>();
                visited = new HashSet<Artist>();

                dft(startingArtist, endingArtist);


            }

        }


    }
    public static void main(String[] args) {
        new SimilarArtists();
    }

    public void dft(Artist currentArtist, Artist destination) {
        currentPath.push(currentArtist);
        visited.add(currentArtist);
        if (currentArtist == destination) {
            printCurrentPath();
        } else {
            for(Edge e : graph.getEdges()) {
                Artist artist = e.getArtist();
                Artist similar = e.getSimilar();

                if (visited.contains(artist) && !visited.contains(similar)) {
                    dft(similar, destination);
                }
                if (!visited.contains(artist) && visited.contains(similar)) {
                    dft(artist, destination);
                }
            }
        }
    }

    public void printCurrentPath() {
        String output = "";

        while (!currentPath.isEmpty()) {
            output = currentPath.pop()+  output;
            if(!currentPath.isEmpty()) {
                output = " \uF0E0 " + output;
            }
        }
        System.out.println("\t" + output);
    }

    public class Artist {
        String name;
        int UID;

        public Artist(String name) {
            this.name = name;
            this.UID = name.hashCode();


        }

        public String getName() {
            return name;
        }

        public int getUID() {
            return UID;
        }

        @Override
        public String toString() {
            return name;
        }

        public boolean equals(Object obj) {
            if (obj == null || obj.getClass() != getClass()) return false;

            Artist otherArtist = (Artist)obj;

            return this.UID == otherArtist.getUID();
        }
    }

    public class Edge {
        Artist artist, similar;
        int UID;

        public Edge(Artist artist, Artist similar) {
            this.artist = artist;
            this.similar = similar;
            this.UID = artist.hashCode() + similar.hashCode();
        }

        public Artist getArtist() {
            return artist;
        }

        public Artist getSimilar() {
            return similar;
        }

        public int getUID() {
            return UID;
        }

        public boolean equals(Object obj) {
            if (obj == null || obj.getClass() != getClass()) return false;

            Edge otherEdge = (Edge)obj;

            return this.UID == otherEdge.getUID();
        }

        @Override
        public String toString() {
            return artist + " is similar to " + similar;
        }

        
    }

    public class Graph {
        HashSet<Artist> artists;
        HashSet<Edge> edges;


        public Graph(){
            artists = new HashSet<>();
            edges = new HashSet<>();
        }

        public HashSet<Artist> getArtists() {
            return artists;
        }

        public HashSet<Edge> getEdges() {
            return edges;
        }

        public void addArtist(Artist a) {
            artists.add(a);
        }

        public void addEdge(Artist a, Artist b) {
            edges.add(new Edge(a, b));
        }
    }


}