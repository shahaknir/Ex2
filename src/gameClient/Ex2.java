package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import gameClient.util.Point3D;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.util.*;

/**
 * this class represent an effective algorithm to play the game
 */
public class Ex2 implements Runnable{

    private static MyFrame _win;
    private static Arena _ar;

    private static dw_graph_algorithms algo;
    private static directed_weighted_graph gg;

    private static HashMap<Integer, List<node_data>> agent_movemenet;

    private static long id;
    private static int level;

    /**
     * this static method makes sure that the user can open the game from the terminal by entering an id and level
     * @param a
     */
    public static void main(String[] a) {

        if (a.length > 0) {

            id = Long.parseLong(a[0]);
            level = Integer.parseInt(a[1]);

            Thread client = new Thread(new Ex2());
            client.start();
        }

        else {

            loginAndLevelSelction();

            Thread client = new Thread(new Ex2());
            client.start();

        }

    }

    /**
     * getting user ID and level selection
    */
    public static void loginAndLevelSelction(){
        id = Integer.parseInt(JOptionPane.showInputDialog(null, "Input your id: ", null));
        JOptionPane.showMessageDialog(null, id);
        level = Integer.parseInt(JOptionPane.showInputDialog(_win, "Use level: ", null));
    }

    /**
     * starting the game according to the init of the game
     */
    @Override
    public void run() {

        game_service game = Game_Server_Ex2.getServer(level); // you have [0,23] games
        game.login(id);

        String g = game.getGraph();
        String pks = game.getPokemons();

        init(game);

        game.startGame();

        _ar.setAgents(Arena.getAgents(game.getAgents(), gg));

        for (CL_Agent a : _ar.getAgents()) {

            agent_movemenet.put(a.getID(), new ArrayList<>());

        }

        _win.setTitle("Ex2:" + game.toString());
        int ind=0;
        long dt=100;

        while(game.isRunning()) {

            moveAgants(game, gg);

            try {
                if(ind%1==0) {_win.repaint();}
                Thread.sleep(dt);
                ind++;
            }

            catch(Exception e) {
                e.printStackTrace();
            }
        }

        String res = game.toString();

        System.out.println(res);
        System.exit(0);
    }

    /**
     * moves the agent according to the best chosen target pokemon and path
     * @param game
     * @param gg
     */
    private static void moveAgants(game_service game, directed_weighted_graph gg) {

        String lg = game.move();

        List<CL_Agent> log = Arena.getAgents(lg, gg);

        _ar.setAgents(log);

        String fs =  game.getPokemons();

        List<CL_Pokemon> pokemons = Arena.json2Pokemons(fs);
        _ar.setPokemons(pokemons);

        List<String> info = new ArrayList<>();

        for(int i=0; i < log.size();i++) {

            CL_Agent ag = log.get(i);

            int id = ag.getID();
            int dest = ag.getNextNode();
            int src = ag.getSrcNode();
            double v = ag.getValue();
            double s = ag.getSpeed();


            info.add("agent id: " + id + ", s:" + s);

            if(dest==-1) {

                pokemons = Arena.json2Pokemons(fs);
                _ar.setPokemons(pokemons);

                if (agent_movemenet.get(id).isEmpty() || agent_movemenet.get(id).size() == 1) {

                    chooseBestPath(ag, pokemons);

                }

                int key = agent_movemenet.get(id).remove(1).getKey();
                game.chooseNextEdge(ag.getID(), key);


            }
        }

        info.add("Time to end: " + game.timeToEnd() / 1000 + " seconds");

        _ar.set_info(info);
    }

    /**
     * this algo find the best path by using Dijkstra that was implements in algo and
     * rating each pokemon by calculating it's value divided to the distance the agent needs to go throw
     *
     * @param agent
     * @param pokemons
     */
    private static void chooseBestPath(CL_Agent agent, List<CL_Pokemon> pokemons) {

        HashMap<Double, CL_Pokemon> preference = new HashMap<>();
        Queue<Double> pok_val = new PriorityQueue<>((x, y) -> Double.compare(y, x));

        for (CL_Pokemon p : pokemons) {

            double distance = algo.shortestPathDist(agent.getSrcNode(), p.get_edge().getSrc());

            double ratio = p.getValue() / distance;

            pok_val.add(ratio);
            preference.put(ratio, p);

        }

        CL_Pokemon pok = preference.get(pok_val.poll());

        for (Map.Entry<Integer, List<node_data>> e : agent_movemenet.entrySet()) {

            List<node_data> list = e.getValue();

            if (!list.isEmpty()) {
                if (list.get(list.size()-1).getKey() == pok.get_edge().getDest()) {
                    pok = preference.get(pok_val.poll());
                }
            }
        }

        List<node_data> shortestPath = algo.shortestPath(agent.getSrcNode(), pok.get_edge().getSrc());
        shortestPath.add(gg.getNode(pok.get_edge().getDest()));

        agent_movemenet.put(agent.getID(), shortestPath);

        System.out.println("a_id: " + agent.getID());
        for(node_data n : shortestPath)
            System.out.print(n.getKey()+"->");
        System.out.println();

    }

    /**
     * loads a graph by reading from JSON and returns a directed weighted graph
     * @param g
     * @return directed weighted graph
     */
    public directed_weighted_graph load_graph(String g) {

        directed_weighted_graph graph = new DWGraph_DS();

        try {

            JSONObject ob = new JSONObject(g);

            JSONArray nodes = ob.getJSONArray("Nodes");
            JSONArray edges = ob.getJSONArray("Edges");

            for (int i = 0; i < nodes.length(); i++) {

                String position = nodes.getJSONObject(i).getString("pos");
                int key = nodes.getJSONObject(i).getInt("id");

                node_data n = new NodeData(key);
                n.setLocation(new Point3D(position));

                graph.addNode(n);

            }

            for (int i = 0; i < edges.length(); i++) {

                double w = edges.getJSONObject(i).getDouble("w");
                int src = edges.getJSONObject(i).getInt("src");
                int dest = edges.getJSONObject(i).getInt("dest");

                graph.connect(src, dest, w);

            }

            return graph;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * initiating a game according to the game server and level chosen by the user
     * this method place the agent by calculating the most valued pokemon
     * @param game
     */
    private void init(game_service game) {

        String fs = game.getPokemons();
        String info = game.toString();

        agent_movemenet = new HashMap<>();

        algo = new DWGraph_Algo();

        String g = game.getGraph();
        gg = load_graph(g);

        algo.init(gg);

        _ar = new Arena();
        _ar.setGraph(gg);
        _ar.setPokemons(Arena.json2Pokemons(fs));
        _win = new MyFrame("test Ex2");
        _win.setSize(1000, 700);
        _win.update(_ar);
        _win.show();


        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("agents");
            System.out.println(info);
            System.out.println(game.getPokemons());

            ArrayList<CL_Pokemon> pokemons = Arena.json2Pokemons(game.getPokemons());

            Queue<CL_Pokemon> pok_val = new PriorityQueue<>((x, y) -> Double.compare(y.getValue(), x.getValue()));

            for(int a = 0;a<pokemons.size();a++) {
                Arena.updateEdge(pokemons.get(a),gg);
                pok_val.add(pokemons.get(a));
            }

            for(int a = 0;a<rs;a++) {

                int ind = a%pokemons.size();
                CL_Pokemon c = pokemons.get(ind);

                game.addAgent(Objects.requireNonNull(pok_val.poll()).get_edge().getSrc());
            }
        }
        catch (JSONException e) {e.printStackTrace();}
    }

}