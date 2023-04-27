package com.busstation.event.listener;

import lombok.Getter;

@Getter
public class MailContent {
    private String html;

    public MailContent() {
        this.html = "<html>" +
                    "<style>" +
                ".container {" +
                "display: flex;" +
                "flex-direction: column;" +
                "}" +
                ".box {" +
                "border: 2px solid red;" +
                "width: 100%;" +
                "display: flex;" +
                "justify-content: center;" +
                "align-items: center;" +
                "margin-bottom: 10px;" +
                "}" +
                ".box-down {" +
                "width: 100%;" +
                "margin: 5px;" +
                "border-bottom: 2px dotted #666363;" +
                "}" +
                "h1 {" +
                "text-align: center;" +
                "font-size: 25px;" +
                "color: blue;" +
                "}" +
                ".box-content {" +
                "display: block;" +
                "padding: 20px;" +
                "background-color: #FF9966;" +
                "width: 90%;" +
                "}" +
                ".box-content p {" +
                "text-align: center;" +
                "}" +
                ".box-detail-service {" +
                "margin-top: 10px;" +
                "border: 0.5px solid rgb(144, 98, 230);" +
                "width: 100%;" +
                "display: flex;" +
                "margin-bottom: 10px;" +
                "flex-direction: column;" +
                "}" +
                ".box-detail-service p {text-align: end; color: #d6ee00; font-size: 30px; font-weight: bold; padding-right: 20px;}" +
                ".box-detail-service .table {padding: 20px;}" +
                ".table .table-title {padding-right: 300px; padding-bottom: 20px;}" +
                ".table .table-content {font-weight: bold;}" +
                ".table .note .table-title {padding-right: 300px; padding-bottom: 20px; font-size: 20; color: blue;}" +
                ".table .note .table-content {font-weight: bold; font-size: 20px; color: #c0f10b;}" +
                    "</style>"+
                    "<body>" +
                    " <div class=\"box\">\n" +
                "                <p class=\"box-content\">\n" +
                "                    Quý khách vui lòng không phản hồi về địa chỉ email này vì chúng tôi sẽ không nhận được. \n" +
                "                    Để gửi phản hồi quý khách vui lòng gửi vào email cs@vexere.com. \n" +
                "                    VeXeRe chỉ tiếp nhận và xử lí các yêu cầu từ cuộc gọi và email của quý khách trong giờ làm việc. \n" +
                "                    Xin cảm ơn quý khách!\n" +
                "                </p>\n" +
                "            </div>\n" +
                "\n" +
                "            <div class=\"box-down\">\n" +
                "                <h1>\n" +
                "                    Cảm ơn quý khách Bùi Sĩ Phong!\n" +
                "                </h1>\n" +
                "                <p>Cảm ơn quý khách đã tin tưởng sử dụng dịch vụ của VeXeRe </p>\n" +
                "            </div>\n" +
                "               <div class=\"box-down\">\n" +
                "                <h1>\n" +
                "                    Hướng dẫn thanh toán!\n" +
                "                </h1>\n" +
                "                <p>1. Quý khách vui lòng thanh toán số tiền 250.000VND \n" +
                "                    cho mã dịch vụ NY1234 cho tài xế khi lên xe.\n" +
                "                </p>\n" +
                "            </div>\n" +
                "\n" +
                "            <div class=\"box-detail-service\">\n" +
                "                <div>\n" +
                "                    <h1>Thông tin dịch vụ</h1>\n" +
                "                </div>\n" +
                "                <div>\n" +
                "                    <p>NY1234</p>\n" +
                "                </div>\n" +
                "                <div class=\"table\">\n" +
                "                    <table>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Hãng xe:</td>\n" +
                "                            <td class=\"table-content\">Kim Anh </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Điểm đón:</td>\n" +
                "                            <td class=\"table-content\">Bưu điện Thành phố Đà Lạt (bên hông)\n" +
                "                                Bãi xe đối diện đài phun nước, Đà Lạt, Lâm Đồng </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Giờ đón (dự kiến):</td>\n" +
                "                            <td class=\"table-content\">08:05 ngày 02/11/2022</td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Điểm trả:</td>\n" +
                "                            <td class=\"table-content\">Bến xe Phía bắc Buôn Ma Thuột\n" +
                "                                , Buôn Ma Thuột, Đắk Lắk \n" +
                "                                </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Số giường/ghế:</td>\n" +
                "                            <td class=\"table-content\">C5D</td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">tuyến:</td>\n" +
                "                            <td class=\"table-content\">Bưu điện Thành phố Đà Lạt (bên hông) - Bến xe Phía bắc Buôn Ma Thuột\n" +
                "                                (xuất phát 08:00 ngày 02/11/2022) \n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        <tr class=\"note\">\n" +
                "                            <td class=\"table-title\">Tổng tiền:</td>\n" +
                "                            <td class=\"table-content\">250.000 VND</td>\n" +
                "                        </tr>\n" +
                "                </table>\n" +
                "                </div>    \n" +
                "            </div>\n" +
                "\n" +
                "            <div class=\"box-detail-service\">\n" +
                "                <div>\n" +
                "                    <h1>Thông tin liên hệ</h1>\n" +
                "                </div>\n" +
                "                <div>\n" +
                "                    <p>NY1234</p>\n" +
                "                </div>\n" +
                "                <div class=\"table\">\n" +
                "                    <table>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Biển số xe:</td>\n" +
                "                            <td class=\"table-content\">47B-026.01 </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Số điện thoại tài xế:</td>\n" +
                "                            <td class=\"table-content\">0976054747 </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Số điện thoại văn phòng:</td>\n" +
                "                            <td class=\"table-content\">(0262) 2.48.48.48 </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Số điện thoại Hotline hỗ trợ:</td>\n" +
                "                            <td class=\"table-content\">(0262) 2.48.48.48 </td>\n" +
                "                        </tr>\n" +
                "                </table>\n" +
                "                </div>    \n" +
                "            </div>\n" +
                "\n" +
                "            <div class=\"box-detail-service\">\n" +
                "                <div>\n" +
                "                    <h1>Thông tin khách hàng</h1>\n" +
                "                </div>\n" +
                "                <div>\n" +
                "                    <p>NY1234</p>\n" +
                "                </div>\n" +
                "                <div class=\"table\">\n" +
                "                    <table>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Họ và tên:</td>\n" +
                "                            <td class=\"table-content\">Kim Anh </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Điện thoại:</td>\n" +
                "                            <td class=\"table-content\">0967497180</td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td class=\"table-title\">Hình thức thanh toán:</td>\n" +
                "                            <td class=\"table-content\">Tại nhà xe</td>\n" +
                "                        </tr>\n" +
                "                </table>\n" +
                "                </div>    \n" +
                "            </div>\n" +
                "            <div class=\"box-down\">\n" +
                "                <h1>\n" +
                "                    Cảm ơn quý khách!\n" +
                "                </h1>\n" +
                "                <p>VeXeRe xin cảm ơn quý khách và rất mong sẽ được phục vụ mọi nhu cầu mua vé xe của \n" +
                "                    quý khách trong tương lai. Quý khách vui lòng đọc kĩ quy chế của VeXeRe tại đây. \n" +
                "                    Trường hợp cần được hỗ trợ trực tiếp về các vấn đề huỷ vé, đổi vé, huỷ chuyến,...\n" +
                "                     xin vui lòng liên hệ dịch vụ chăm sóc khách hàng của chúng tôi: \n" +
                "                </p>\n" +
                "            </div>\n" +
                "        </div>" +
                    "</body>"+
                    "</html>";
    }
}
