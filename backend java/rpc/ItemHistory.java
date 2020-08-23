package rpc;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import db.MySQLConnection;
import entity.Item;


public class ItemHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ItemHistory() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(403);
			return;
		}

		String userId = request.getParameter("user_id");
		
		MySQLConnection connection = new MySQLConnection();
		Set<Item> items = connection.getFavoriteItems(userId);
		connection.close();
		
		JSONArray array = new JSONArray();
		for (Item item : items) {
			JSONObject obj = item.toJSONObject();
			obj.put("favorite", true); //to show in front-end that the heart is solid or not
			array.put(obj);
		}
		RpcHelper.writeJsonArray(response, array);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(403);
			return;
		}
		MySQLConnection connection = new MySQLConnection();
		JSONObject input = new JSONObject(IOUtils.toString(request.getReader()));
		String userId = input.getString("user_id");
		//transform JSONObject to item object
		Item item = RpcHelper.parseFavoriteItem(input.getJSONObject("favorite"));
		connection.setFavoriteItems(userId, item);
		connection.close();
		//testing
		RpcHelper.writeJsonObject(response, new JSONObject().put("result", "SUCCESS"));
	}


	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(403);
			return;
		}
		MySQLConnection connection = new MySQLConnection();
		JSONObject input = new JSONObject(IOUtils.toString(request.getReader()));
		String userId = input.getString("user_id");
		Item item = RpcHelper.parseFavoriteItem(input.getJSONObject("favorite"));
		//unset favorite
		connection.unsetFavoriteItems(userId, item.getItemId());
		connection.close();
		RpcHelper.writeJsonObject(response, new JSONObject().put("result", "SUCCESS"));
	}

}
