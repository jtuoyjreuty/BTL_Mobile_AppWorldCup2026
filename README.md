# Ứng Dụng Mobile: Lịch Thi Đấu World Cup 2026

Bài tập lớn môn học: Lập trình mobile cơ bản

Giảng viên hướng dẫn: ThS. Bùi Đức Thọ

Lớp: 12523T.1



1. Giới thiệu đề tài

* Bối cảnh: Bóng đá là môn thể thao vua. Người hâm mộ luôn có nhu cầu cập nhật lịch thi đấu, tỷ số và bảng xếp hạng một cách nhanh chóng, chính xác ở bất cứ đâu.

* Vấn đề hiện tại: Thông tin thường phân tán trên nhiều nguồn, phụ thuộc hoàn toàn vào kết nối Internet (online) gây bất tiện khi người dùng ở khu vực mạng yếu.

* Mục tiêu / Giải pháp: Xây dựng một ứng dụng di động Android gọn nhẹ, cho phép tra cứu thông tin giải đấu (Lịch, Bảng xếp hạng, Vua phá lưới) Offline nhờ hệ quản trị cơ sở dữ liệu cục bộ. Tích hợp phân hệ Quản trị (Admin) để cập nhật dữ liệu dễ dàng.



2. Thiết kế Cơ sở dữ liệu (Database)

Ứng dụng sử dụng SQLite để lưu trữ cục bộ, đảm bảo tốc độ truy vấn cực nhanh và không bị giật lag. Cấu trúc các bảng (Tables) chính bao gồm:

* Nguoi\_dung: Quản lý người dùng và phân quyền (Admin/User). Gồm các trường: id, taikhoan, matkhau, quyen.

* Tran\_dau: Quản lý thông tin trận đấu. Gồm: id, doiNha, doiKhach, thoiGian, tyleKeo, baiNhanDinh.

* Bang\_xep\_hang: Quản lý điểm số các bảng đấu. Gồm: id, tenBang, tenDoi, p, w, l, gd, pts, d.

* Vua\_pha\_luoi: Quản lý cầu thủ ghi bàn. Gồm: id, tenCauThu, quocGia, viTri, soBanThang.

* (Điểm đặc biệt: Tối ưu thiết kế không ràng buộc khóa ngoại phức tạp để tăng tốc độ truy xuất trên thiết bị di động).



3. Luồng chức năng (Use Cases)

Hệ thống được thiết kế phục vụ 2 đối tượng chính:

Người dùng (User):

* Xem thông tin giải đấu: Xem lịch thi đấu, tra cứu Bảng xếp hạng vòng bảng, xem Top ghi bàn (Vua phá lưới).

* Cài đặt: Tùy chỉnh ứng dụng (Bật/tắt Dark Mode, Hiển thị giờ Việt Nam GMT+7, Xóa bộ nhớ đệm).

Quản trị viên (Admin):

* Đăng nhập: Xác thực an toàn vào hệ thống.

* Quản trị dữ liệu (CRUD): Thêm, Sửa, Xóa thông tin các trận đấu, nhận định, tỷ số, bảng xếp hạng và danh sách cầu thủ trực tiếp trên App mà không cần can thiệp mã nguồn.

* Đổi mật khẩu / Quản lý tài khoản.



4. Công nghệ \& Kiến trúc sử dụng

* Nền tảng: Android (Hỗ trợ từ Android API 24+ trở lên).

* Ngôn ngữ: Java (OOP - Áp dụng chặt chẽ Lập trình hướng đối tượng).

* Môi trường \& Công cụ: Android Studio (IDE), Android Emulator.

* Database: SQLite (Lưu trữ offline cục bộ).

* UI/UX Design: Mobile-first, sử dụng XML Layout, kết hợp RecyclerView và CardView để tối ưu danh sách hiển thị mượt mà. Drawable (Icon, Vector) sắc nét.



5. Kết quả \& Hướng phát triển

Kết quả đạt được:

* Ứng dụng hoạt động mượt mà, chuyển cảnh nhanh.

* Giao diện (UI) hiện đại, trực quan, đáp ứng đúng nhu cầu tra cứu thông tin World Cup.

* Chức năng Admin quản lý dữ liệu SQLite hoạt động chính xác.

Hạn chế: Dữ liệu vẫn lưu trữ cục bộ, chưa thể đồng bộ tự động giữa nhiều thiết bị người dùng với nhau.

Hướng phát triển tương lai:

* Tích hợp Cloud Database (Firebase / AWS) để đồng bộ dữ liệu thời gian thực (Real-time).

* Thêm tính năng Thông báo đẩy (Push Notifications) khi có trận đấu sắp diễn ra hoặc có bàn thắng.

* Hỗ trợ đa ngôn ngữ và chế độ dự đoán tỷ số cho cộng đồng người dùng.



6. Hướng dẫn chạy dự án

Cài đặt môi trường:

* Cài đặt Android Studio (Phiên bản mới nhất).

* Tải và cài đặt máy ảo (Android Emulator) hoặc kết nối thiết bị thật có hệ điều hành Android 7.0 (API 24) trở lên.

Cách chạy (Build \& Run):

* Clone dự án về máy:

* git clone \[Link-GitHub-Của-Bạn]



* Mở Android Studio -> Chọn File -> Open -> Chọn thư mục dự án vừa tải về.

* Chờ Gradle đồng bộ xong các thư viện cần thiết.

* Nhấn nút Run 'app' (hoặc tổ hợp phím Shift + F10) để cài đặt và chạy ứng dụng lên máy ảo/máy thật.

* Tài khoản Admin mặc định để test hệ thống:

* Tài khoản: admin

* Mật khẩu: 123



7. Cấu trúc thư mục dự án

Thư mục được tổ chức theo chuẩn cấu trúc Android Studio:

```text
├── app/
│   ├── src/main/java/com/example/btlwcc/  # Source code Java (Activities, Models, DatabaseHelper)
│   ├── src/main/res/                      # Giao diện XML (layout, drawable, values, mipmap)
│   └── AndroidManifest.xml                # Cấu hình cấp quyền và luồng màn hình
├── data/                                  # Chứa file SQLite mẫu hoặc schema export
├── reports/                               # Báo cáo tổng kết môn học (PDF/Docx)
├── slides/                                # Slide thuyết trình bảo vệ dự án (PPTX)
├── README.md                              # File hướng dẫn chạy dự án
└── .gitignore                             # Cấu hình bỏ qua file build, build/gradle
```

8. Tác giả
* **Họ tên:** Nguyễn Văn Hiếu
* **Mã SV:** 10123126
* **Lớp:** 12523T.1
* **Họ tên:** Hoàng Anh Phúc
* **Mã SV:** 10123256
* **Lớp:** 12523T.1

