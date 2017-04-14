import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImgDown {
	public static void main(String[] args) {
		Document doc = null;
		try {
			doc = Jsoup.connect("https://www.douban.com/").get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<String> urls = new ArrayList<>();
		Elements imgs = doc.select("img[src]");
		String src = "";
		String data_origin = "";
		for (int i = 0; i < imgs.size(); i++) {
			if (!"".equals(imgs.get(i).attr("src"))) {
				urls.add(imgs.get(i).attr("src"));
			}
			if (!"".equals(imgs.get(i).attr("data-origin"))) {
				urls.add(imgs.get(i).attr("data-origin"));
			}
		}
		String url = "";
		byte[] data;
		try {
			int i = 0;
			String fileName = "";
			for (String s : urls) {
				fileName = i++ + s.substring(s.lastIndexOf("."));
				data = url2Data(s);
				FileOutputStream out = new FileOutputStream("C:\\Users\\lxl\\Desktop\\test" + File.separator + fileName);
				out.write(data);
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("下载完成");
		System.out.println("总计" + urls.size());
	}

	private static byte[] url2Data(String string) throws IOException {
		OkHttpClient client = new OkHttpClient();
		// 发送请求Request，获得响应response
		Request request = new Request.Builder().url(string).build();

		Response response = client.newCall(request).execute();
		return response.body().bytes();
	}
}
