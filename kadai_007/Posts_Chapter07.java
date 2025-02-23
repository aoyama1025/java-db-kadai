package kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Posts_Chapter07 {
	public static void main(String[] args) {
		
		Connection con = null;
		PreparedStatement statement = null;
		
		//投稿データ
		String[][] posts = {
				{"1003", "2023-02-08", "昨日の夜は徹夜でした・・", "13"},
				{"1002", "2023-02-08", "お疲れ様です！", "12"},
				{"1003", "2023-02-09", "今日も頑張ります！", "18"},
				{"1001", "2023-02-09", "無理は禁物ですよ！", "17"},
				{"1002", "2023-02-10", "明日から連休ですね！", "20"},
		};
		
		try {
			//データベースに接続
			con =  DriverManager.getConnection(
	                "jdbc:mysql://localhost/challenge_java",
	                "root",
	                "Sugiyuma3!"
	            );
			
			System.out.println("データベース接続成功:" + con);
			
			//SQLクエリを準備
			String sql = "INSERT INTO posts (user_id,posted_at,post_content,likes) VALUES (?,?,?,?);";
			statement = con.prepareStatement(sql);

			int totalRows = 0;
			
			for(int i = 0; i < posts.length; i++) {
				statement.setInt(1, Integer.parseInt(posts[i][0])); //ユーザーID
				statement.setString(2, posts[i][1]); //投稿日時
				statement.setString(3, posts[i][2]); //投稿内容
				statement.setInt(4, Integer.parseInt(posts[i][3])); //いいね数
			
				
				//SQLクエリを実行
                int rowCnt = statement.executeUpdate();
                totalRows += rowCnt;
			}
				System.out.println("レコード追加を実行します");
                System.out.println( totalRows + "件のレコードが追加されました");
			
                
                //投稿データを検索する
                String selectsql ="SELECT posted_at,post_content,likes FROM posts WHERE user_id = 1002;";
                System.out.println("ユーザーIDが1002のレコードを検索しました");
                
                //SQLクエリを実行
                ResultSet result = statement.executeQuery(selectsql);
                while(result.next()) {
                	Date postedAt = result.getDate("posted_at");
                	String content = result.getString("post_content");
                	int likes = result.getInt("likes");
                	System.out.println(result.getRow() + "件目：投稿日時=" + postedAt + "／投稿内容=" + content + "／いいね数=" + likes);
            }
		}
			catch(SQLException e) {
				System.out.println("エラー発生：" + e.getMessage());
	        } finally {
	            // 使用したオブジェクトを解放
	            if( statement != null ) {
	                try { statement.close(); } catch(SQLException ignore) {}
	            }
	            if( con != null ) {
	                try { con.close(); } catch(SQLException ignore) {}
	            }
			}
	}
}
