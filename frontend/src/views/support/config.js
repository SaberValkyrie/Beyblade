import { ref } from 'vue';
import StompClient from '@stomp/stompjs';

const config = {
    imageUrl: 'http://localhost:8080/', // Đường dẫn tới thư mục chứa hình ảnh
    stompClient: null // Biến để lưu trữ client Stomp
};

// Thiết lập kết nối WebSocket
const connectWebSocket = () => {
    const socket = new WebSocket('ws://localhost:8080/ws'); // Thay đổi URL WebSocket tùy theo cài đặt của bạn
    config.stompClient = StompClient.over(socket);
    config.stompClient.connect({}, () => {
        console.log('Connected to WebSocket');
        // Xử lý logic khi kết nối thành công
    });
};

// Ngắt kết nối WebSocket
const disconnectWebSocket = () => {
    if (config.stompClient) {
        config.stompClient.disconnect(() => {
            console.log('Disconnected from WebSocket');
            // Xử lý logic khi ngắt kết nối
        });
    }
};

export { config, connectWebSocket, disconnectWebSocket };
