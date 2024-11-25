import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class App {
    public static String token_house = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6IjYwYzc5MTlmLWU3NmQtNDZiNy05YjQwLThkMjc0ODViYmZiYSIsImlhdCI6MTczMDk5ODg3NCwic3ViIjoiZGV2ZWxvcGVyL2YzMWE0YWIwLTZmYTQtOTVkMS0wNmQxLTFlZmRiOGIyMzQzNSIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjQ5LjE2Ny4xNTUuMTQzIl0sInR5cGUiOiJjbGllbnQifV19.1z5VQd00uf5MtK2myPZAIF3K2D-Fj_uJLMO9pPFAGWYh885klFGULhMbGjZKzkMNoM9LuEcDREImBNRig-PPfA";
    public static String token_school = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6Ijk3YTlhYjAwLWJmZGItNDNkNS1hMmZlLTRmYTU1Nzk0MTFlMyIsImlhdCI6MTczMjI0OTk0MCwic3ViIjoiZGV2ZWxvcGVyL2YzMWE0YWIwLTZmYTQtOTVkMS0wNmQxLTFlZmRiOGIyMzQzNSIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjIxMC4xMTkuMjM3LjEwNCJdLCJ0eXBlIjoiY2xpZW50In1dfQ.KtyDXBmDk9c_PsRxVOvV1XsfSR3trjfpuxETNUS4FCr9vq9LKhQo9BmlmUxgsqIHk6QE43cMpMYGb5nQqy-6MA";
    public static String token_brain = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6IjJkYmQwMDA1LWY4NTAtNDQ3OC1hYmQxLTllMmUzZDhhODc2MSIsImlhdCI6MTczMTc2MDM0Miwic3ViIjoiZGV2ZWxvcGVyL2YzMWE0YWIwLTZmYTQtOTVkMS0wNmQxLTFlZmRiOGIyMzQzNSIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjIxMC4xMTkuMjM3LjEwNCIsIjE0LjUyLjE3Ny4yMTEiXSwidHlwZSI6ImNsaWVudCJ9XX0.C4m_fCEqs6CFjPNfm1-9BTOGg3tki3ixjue5FuYJB9s6KPPTUY_nko4M5nqCNMPPQ7sxEViNNvVAUX-ANUAg3A";
    public static String token = token_house;
    public static void main(String[] args) throws Exception {
        // printMembers();
        // System.out.println("----------------------------");
        Scanner kb = new Scanner(System.in);
        System.out.println("1. printWarInfo\n2. connect to database(local)\n3. exit\n >> ");
        int input = kb.nextInt();
        switch (input) {
            case 1:
                printWarInfo();
                break;
            case 2:
                connectDatabase();
                break;
            default:

        }
        kb.close();
    }

    public static void connectDatabase() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost/coc";
            String user = "root", passwd = "2421";
            con = DriverManager.getConnection(url, user, passwd);
            System.out.println(con);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // database operations ...

        try {
            if (con != null && !con.isClosed())
                con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printWarInfo() {
        JSONObject war = getWarInfo();

        String state = war.getString("state");
        if(!state.equals("inWar")) {
            System.out.println("ERROR : not in war");
            return;
        }
        int teamSize = war.getInt("teamSize");
        JSONArray opMembers = war.getJSONObject("opponent").getJSONArray("members");
        HashMap<String, Integer> opponent = new HashMap<String, Integer>();
        for (int i = 0; i < opMembers.length(); i++) {
            JSONObject omember = opMembers.getJSONObject(i);

            String oTag = omember.getString("tag");
            int oPosition = omember.getInt("mapPosition");

            opponent.put(oTag, oPosition);
        }

        JSONArray members = war.getJSONObject("clan").getJSONArray("members");

        ArrayList<WarAtkData> atkDatas = new ArrayList<WarAtkData>();
        boolean[] attackedCheckByPosition = new boolean[51];

        HashMap<Integer, String> getNameByPosition = new HashMap<Integer, String>();

        for (int i = 0; i < members.length(); i++) {
            JSONObject member = members.getJSONObject(i);
            JSONArray atks = null;

            String tag = member.getString("tag");
            String name = member.getString("name");
            int position = member.getInt("mapPosition");

            getNameByPosition.put(position, name);

            System.out.println(tag + ", " + position);

            if (member.has("attacks")) {
                atks = member.getJSONArray("attacks");

                for (int j = 0; j < atks.length(); j++) {
                    JSONObject atk = atks.getJSONObject(j);

                    String defTag = atk.getString("defenderTag");
                    int defPosition = opponent.get(defTag);
                    int stars = atk.getInt("stars");
                    int order = atk.getInt("order");

                    System.out.println(order + ". " + position + " >> " + defPosition + " : star - " + stars);
                    atkDatas.add(new WarAtkData(order, name, position, defPosition, stars));
                }
            } else {
                System.out.println("not attacked");
            }
        }
        Collections.sort(atkDatas);
        System.out.println("########################################################");
        for(int i=0; i<teamSize; i++) {
            System.out.println((i+1) + ". " + getNameByPosition.get(i+1));
        }
        System.out.println("========================================================");
        for (WarAtkData atk : atkDatas) {
            System.out.println(atk.position + " " + atk.defPosition + " " + atk.stars);
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        ArrayList<MemberIllegalInfo> memberIllegalInfos = new ArrayList<MemberIllegalInfo>();
        String[] illegalString = {"ILLEGAL-Not Attacked", "ILLEGAL-1 [타번호공격] ( at first attack, attacked another num (not opponent destroyed yet) )", "ILLEGAL-2 [동번 미완파 후 상위공격] ( at second attack, attacked upper num without complete destroy opponent )"};

        for (int i = 0; i < atkDatas.size(); i++) {
            WarAtkData atk = atkDatas.get(i);
            if (attackedCheckByPosition[atk.position]) { // attacked (second atk)
                WarAtkData firstAtkData = new WarAtkData();
                if (atk.defPosition < atk.position) {
                    for (WarAtkData warAtkData : atkDatas) {
                        if (warAtkData.position == atk.position) {
                            firstAtkData.set(warAtkData);
                            break;
                        }
                    }
                    if (firstAtkData.position == firstAtkData.defPosition && firstAtkData.stars <= 2) {
                        memberIllegalInfos.add(new MemberIllegalInfo(atk.position, atk.name,
                        new String(illegalString[2])));
                    }
                }
                else if(atk.defPosition == atk.position) {
                    if(memberIllegalInfos.contains(new MemberIllegalInfo(atk.position, atk.name, new String(illegalString[1])))) {
                        memberIllegalInfos.remove(new MemberIllegalInfo(atk.position, atk.name, new String(illegalString[1])));
                    }
                }

            } else { // not attacked (first atk)
                attackedCheckByPosition[atk.position] = true;
                if (atk.position != atk.defPosition) {
                    // check if already attacked (star>=2)
                    boolean halfDestroyed = false;
                    for (int j = 0; j < i; j++) {
                        WarAtkData w = new WarAtkData(atkDatas.get(j));
                        // System.out.println("checking : " + w.position + " >> " + w.defPosition + ", "
                        // + w.stars);
                        if (w.defPosition == atk.position && w.stars >= 2) {
                            halfDestroyed = true;
                            break;
                        }
                    }
                    if (!halfDestroyed) { // illegal
                        memberIllegalInfos.add(new MemberIllegalInfo(atk.position, atk.name,
                        new String(illegalString[1])));
                    }
                }
            }
        }
        for (int i = 1; i <= teamSize; i++) {
            if (!attackedCheckByPosition[i]) {
                memberIllegalInfos.add(new MemberIllegalInfo(i, getNameByPosition.get(i), new String(illegalString[0])));
            }
        }
        Collections.sort(memberIllegalInfos);

        for (MemberIllegalInfo iInfo : memberIllegalInfos) {
            System.out.println(String.format("%02d. %-20s\t: %s", iInfo.position, iInfo.name, iInfo.faultDetail));
        }

    }

    public static void printMembers() {
        JSONArray members = getMembers().getJSONArray("items");

        for (int i = 0; i < members.length(); i++) {
            JSONObject member = members.getJSONObject(i);

            String name = member.getString("name");
            String tag = member.getString("tag");
            int donations = member.getInt("donations");

            System.out.println(name + ", " + tag + ", " + donations);
        }
    }

    public static JSONObject getWarInfo() {
        HttpURLConnection conn = null;
        JSONObject responseJson = null;
        try {

            //String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6IjYwYzc5MTlmLWU3NmQtNDZiNy05YjQwLThkMjc0ODViYmZiYSIsImlhdCI6MTczMDk5ODg3NCwic3ViIjoiZGV2ZWxvcGVyL2YzMWE0YWIwLTZmYTQtOTVkMS0wNmQxLTFlZmRiOGIyMzQzNSIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjQ5LjE2Ny4xNTUuMTQzIl0sInR5cGUiOiJjbGllbnQifV19.1z5VQd00uf5MtK2myPZAIF3K2D-Fj_uJLMO9pPFAGWYh885klFGULhMbGjZKzkMNoM9LuEcDREImBNRig-PPfA";
            URL url = new URL(
                    "https://api.clashofclans.com/v1/clans/" + URLEncoder.encode("#2QV2VL99J", "UTF-8")
                            + "/currentwar");
            System.err.println("making connection to : " + "https://api.clashofclans.com/v1/clans/"
                    + URLEncoder.encode("#2QV2VL99J", "UTF-8") + "/currentwar");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "Bearer " + token);
            System.err.println("Authorization: Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json");

            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                responseJson = new JSONObject(sb.toString());
            } else if (responseCode == 400) {
                System.err.println("400 : Client provided incorrect parameters for the request.");
            } else if (responseCode == 403) {
                System.err.println(
                        "403 : Access denied, either because of missing/incorrect credentials or used API token does not grant access to the requested resource.");
            } else if (responseCode == 404) {
                System.err.println("404 : Resource was not found");
            } else {
                System.err.println("error code > 404 : unknown");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            System.err.println("not JSON Format response");
            e.printStackTrace();
        }

        return responseJson;
    }

    public static JSONObject getMembers() {
        HttpURLConnection conn = null;
        JSONObject responseJson = null;
        try {
            URL url = new URL(
                    "https://api.clashofclans.com/v1/clans/" + URLEncoder.encode("#2QV2VL99J", "UTF-8") + "/members");
            System.err.println("making connection to : " + "https://api.clashofclans.com/v1/clans/"
                    + URLEncoder.encode("#2QV2VL99J", "UTF-8") + "/members");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "Bearer " + token);
            System.err.println("Authorization: Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json");

            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                responseJson = new JSONObject(sb.toString());
            } else if (responseCode == 400) {
                System.err.println("400 : Client provided incorrect parameters for the request.");
            } else if (responseCode == 403) {
                System.err.println(
                        "403 : Access denied, either because of missing/incorrect credentials or used API token does not grant access to the requested resource.");
            } else if (responseCode == 404) {
                System.err.println("404 : Resource was not found");
            } else {
                System.err.println("error code > 404 : unknown");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            System.err.println("not JSON Format response");
            e.printStackTrace();
        }

        return responseJson;
    }

    static class WarAtkData implements Comparable<WarAtkData> {
        public int order;
        public int position;
        public int defPosition;
        public int stars;
        public String name;
    
        public WarAtkData(int order, String name, int position, int defPosition, int stars) {
            this.order = order;
            this.name = new String(name);
            this.position = position;
            this.defPosition = defPosition;
            this.stars = stars;
        }
    
        public WarAtkData() {
        }
    
        public WarAtkData(WarAtkData w) {
            this.order = w.order;
            this.name = new String(w.name);
            this.position = w.position;
            this.defPosition = w.defPosition;
            this.stars = w.stars;
        }
    
        public void set(WarAtkData w) {
            this.order = w.order;
            this.name = new String(w.name);
            this.position = w.position;
            this.defPosition = w.defPosition;
            this.stars = w.stars;
        }
    
        @Override
        public int compareTo(WarAtkData o) {
            return this.order - o.order;
        }
    }

    static class MemberIllegalInfo implements Comparable<MemberIllegalInfo> {
        int position;
        String name;
        String faultDetail;
    
    
        public MemberIllegalInfo(int position, String name, String faultDetail) {
            this.position = position;
            this.name = name;
            this.faultDetail = faultDetail;
        }
    
        @Override
        public int compareTo(MemberIllegalInfo o) {
            return this.position - o.position;
        }

        @Override
        public boolean equals(Object obj) {
            MemberIllegalInfo o = (MemberIllegalInfo)obj;
            if(position == o.position && name.equals(o.name) && faultDetail.equals(o.faultDetail)) return true;
            return false;
        }
    
    }
    
}