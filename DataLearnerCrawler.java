package test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*******
 * created by DuFei at 2017.08.25 21:00
 * web crawler example
 * ******/

public class DataLearnerCrawler {

    public static void main(String[] args) throws UnsupportedEncodingException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dates  = sdf.format(date);
        String url = "http://www.safe.gov.cn/AppStructured/hlw/jsonRmb.do?date="+dates;
        String rawHTML = null;
        try {
            rawHTML = getHTMLContent(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //将当前页面转换成Jsoup的Document对象
        Document doc = Jsoup.parse(rawHTML);

        //获取所有的博客列表集合
        Elements blogList = doc.select("body");

       String s = blogList.get(0).getAllElements().text();
       s = s.replaceAll("\\[","");
       s = s.replaceAll("\\]","");
        String[] arry = s.split(",");
        for(int i = 0; i<arry.length ;){
            List<String> list = new ArrayList<String>();
            for(int k = 0 ; k < 4; k++,i++){
                list.add(arry[i]);
            }
            System.out.println(list);
        }
    }

    //根据url地址获取对应页面的HTML内容，我们将上一节中的内容打包成了一个方法，方便调用
    private static String getHTMLContent( String url ) throws IOException {

        //建立一个新的请求客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //使用HttpGet方式请求网址
        HttpGet httpGet = new HttpGet(url);

        //获取网址的返回结果
        CloseableHttpResponse response = httpClient.execute(httpGet);

        //获取返回结果中的实体
        HttpEntity entity = response.getEntity();

        String content = EntityUtils.toString(entity);

        //关闭HttpEntity流
        EntityUtils.consume(entity);

        return content;

    }

}
