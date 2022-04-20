package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.ExamTopic;
import com.example.demo.mapper.ExamTopicMapper;
import com.example.demo.pdf.MyHeaderFooter;
import com.example.demo.service.ExamTopicService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ExamTopicServiceImpl.java
 * @Description
 * @Author Vince
 * @CreateTime 2022年04月11日 10:14:00
 */
@Service
@Slf4j
public class ExamTopicServiceImpl extends ServiceImpl<ExamTopicMapper, ExamTopic> implements ExamTopicService {

    // 定义全局的字体静态变量
    private static Font titlefont;
    private static Font headfont;
    private static Font keyfont;
    private static Font textfont;
    // 最大宽度
    private static int maxWidth = 520;

    // 静态代码块
    static {
        try {
            // 不同字体（这里定义为同一种字体：包含不同字号、不同style）
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            titlefont = new Font(bfChinese, 16, Font.BOLD);
            headfont = new Font(bfChinese, 14, Font.BOLD);
            keyfont = new Font(bfChinese, 10, Font.BOLD);
            textfont = new Font(bfChinese, 10, Font.NORMAL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void genPdf(String filename, Integer type, Integer limitNum) {
        log.info("开始生成pdf....");
        try {
            // 1.新建document对象
            Document document = new Document(PageSize.A4);// 建立一个Document对象

            // 2.建立一个书写器(Writer)与document对象关联
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String format = formatter.format(LocalDateTime.now());
            File file = new File(filename + format + ".pdf");
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
//            writer.setPageEvent(new Watermark("HELLO ITEXTPDF"));// 水印
            writer.setPageEvent(new MyHeaderFooter());// 页眉/页脚

            // 3.打开文档
            document.open();
            document.addTitle("Title@PDF-Java");// 标题
            document.addAuthor("Author@umiz");// 作者
            document.addSubject("Subject@iText pdf sample");// 主题
            document.addKeywords("Keywords@iTextpdf");// 关键字
            document.addCreator("Creator@umiz`s");// 创建者

            // 4.向文档中添加内容
            generatePDF(document, type, limitNum);

            // 5.关闭文档
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("结束生成pdf....");
    }

    @SneakyThrows
    private void generatePDF(Document document, Integer type, Integer limitNum) {
        // 段落
        Paragraph paragraph = new Paragraph("案例考点！", titlefont);
        paragraph.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
        paragraph.setIndentationLeft(12); //设置左缩进
        paragraph.setIndentationRight(12); //设置右缩进
        paragraph.setFirstLineIndent(24); //设置首行缩进
        paragraph.setLeading(20f); //行间距
        paragraph.setSpacingBefore(5f); //设置段落上空白
        paragraph.setSpacingAfter(10f); //设置段落下空白

        // 直线
        Paragraph p1 = new Paragraph();
        p1.add(new Chunk(new LineSeparator()));

        // 点线
        Paragraph p2 = new Paragraph();
        p2.add(new Chunk(new DottedLineSeparator()));

//
//        // 添加图片
//        Image image = Image.getInstance("https://img-blog.csdn.net/20180801174617455?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zNzg0ODcxMA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70");
//        image.setAlignment(Image.ALIGN_CENTER);
//        image.scalePercent(40); //依照比例缩放

        document.add(paragraph);
        // 添加题目
        String answer = addTopic(document, type, limitNum);
        document.add(p2);
        document.add(p1);
        document.newPage();
        // 添加答案
        Font font = new Font(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));
        document.add(new Paragraph(answer, font));
    }


    @SneakyThrows
    private String addTopic(Document document, Integer type, Integer limitNum) {
        List<ExamTopic> examTopicList = this.list(new LambdaQueryWrapper<ExamTopic>().eq(ExamTopic::getType, type));
        Collections.shuffle(examTopicList); // 打乱顺序
        List<ExamTopic> list = examTopicList.stream().limit(limitNum).collect(Collectors.toList()); // 限制数量
        //第一种：使用iTextAsian.jar包中的字体

        Font font = new Font(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));
        StringBuffer answer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            ExamTopic examTopic = list.get(i);
            // 获取题目
            String topic = examTopic.getTopic();

            Paragraph p3 = new Paragraph((i + 1) + " ." + topic+"["+examTopic.getId()+"]", font);
            document.add(p3);
            Paragraph p4 = new Paragraph("        ");
            document.add(p4);
            answer.append("\r\n");
//            Paragraph p5 = new Paragraph("\r\n");
//            document.add(p5);
            answer.append((i + 1) + " ." + topic);
            answer.append("\r\n");
            answer.append(examTopic.getReferAnswer());
            answer.append("\r\n");
        }
        return answer.toString();
    }
}
