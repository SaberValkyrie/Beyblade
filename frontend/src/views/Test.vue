<template>
  <app-header></app-header>
  <br><br><br><br><br>

  <div class="container">
    <div class="main-body">
      <div class="row">
        <div class="left-column">
          <div class="card">
            <div class="card-body">
              <div class="d-flex flex-column align-items-center text-center">
                <img :src="selectedBey.images" alt="Admin" class="rounded-circle p-1 bg-primary" width="110">
                <div class="mt-3">
                  <h4>{{ selectedBey.name }}</h4>
                  <p class="text-secondary mb-1">LV: {{ selectedBey.season }}</p>
                  <p class="text-muted success">Giá :{{ selectedBey.price }} BeyPoint</p>
                </div>
                <div style="display:flex;zoom: 70%">
                  <button style="background-color: brown;margin-left: 1rem;margin-right: 1rem" @click="setTypeBey(selectedType)">Hủy</button>
                  <button @click="accept()">OK</button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="right-column">
          <div class="card">
            <div class="card-body">
              <h5 class="d-flex align-items-center mb-3">Chỉ Số</h5>
              <p>Tấn Công : {{ convert(selectedBey.power) }}</p>
              <div class="progress mb-3" style="height: 5px">
                <div class="progress-bar bg-primary" role="progressbar" :style="{ width: (selectedBey.power / 10000) * ((8 - selectedBey.season) * 2) + '%' }" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"></div>
              </div>
              <p>Sức Bền:{{ convert(selectedBey.hp) }}</p>
              <div class="progress mb-3" style="height: 5px">
                <div class="progress-bar bg-danger" role="progressbar" :style="{ width: (selectedBey.hp / 10000) * ((8 - selectedBey.season) * 2) + '%' }" aria-valuemin="0" aria-valuemax="100"></div>
              </div>
              <p>Tỉ Lệ Né Đòn : {{ convert(selectedBey.tiLeNeDon) }}%</p>
              <div class="progress mb-3" style="height: 5px">
                <div class="progress-bar bg-success" role="progressbar" :style="{ width: (selectedBey.tiLeNeDon) + '%' }" aria-valuenow="89" aria-valuemin="0" aria-valuemax="100"></div>
              </div>
              <p>Tỉ Lệ Chí Mạng :{{ convert(selectedBey.crit) }}%</p>
              <div class="progress mb-3" style="height: 5px">
                <div class="progress-bar bg-warning" role="progressbar" :style="{ width: (selectedBey.crit) + '%' }" aria-valuemax="100"></div>
              </div>
              <p>Tỉ Lệ Gây Burst :{{ ((5 - selectedBey.type.id )* 3) }}%</p>
              <div class="progress" style="height: 5px">
                <div class="progress-bar bg-info" role="progressbar" :style="{ width: ((5 - selectedBey.type.id )* 3) + '%' }" aria-valuenow="66" aria-valuemin="0" aria-valuemax="100"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'; // Import Axios
import { baseURL } from '@/router/index';
import Header from '/src/views/support/Header.vue'
import Foooter from '/src/views/support/Footer.vue'
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';

export default {
  components: {
    'app-header': Header,
    'app-footer': Foooter,
  },

  data() {
    return {
      // service: new GameService(),
      baseUrl: baseURL,
      item: { "code": "565327b1-af53-409c-be7a-89ebb520c616", "beyBlade": { "id": 130, "name": "Air Knight 12Expand Eternal", "images": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTTMZ1LLrsUIWap4OZmRjNcO3Ug4TrmrzYgK5Fkg3DBlQ&s", "type": { "id": 3, "name": "Bền Bỉ", "images": "https://static.wikia.nocookie.net/beyblade/images/a/a8/Hasbro_BB_type_logo_stamina.png" }, "price": 8010, "isBoss": false, "power": 100, "hp": 400000, "tiLeNeDon": 10, "crit": 1, "season": 3, "spin": "R", "boss": false }, "quantity": 1, "price": 1602, "day": 2 },
      code: 'b1-af53-409c-be7a-89ebb520c616',
      selectedBey: { "id": 130, "name": "Air Knight 12Expand Eternal", "images": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTTMZ1LLrsUIWap4OZmRjNcO3Ug4TrmrzYgK5Fkg3DBlQ&s", "type": { "id": 3, "name": "Bền Bỉ", "images": "https://static.wikia.nocookie.net/beyblade/images/a/a8/Hasbro_BB_type_logo_stamina.png" }, "price": 8010, "isBoss": false, "power": 100, "hp": 400000, "tiLeNeDon": 10, "crit": 1, "season": 3, "spin": "R", "boss": false }
    };
  },
  created() {
    // this.getItemShop()
  },
  methods: {
    convert(power) {
      const formatter = new Intl.NumberFormat('vi-VN', { maximumFractionDigits: 1 });

      if (power >= 1e9) {
        return formatter.format(power / 1e9) + 'Tỷ';
      } else if (power >= 1e6) {
        return formatter.format(power / 1e6) + 'Tr';
      } else if (power >= 1e3) {
        return formatter.format(power / 1e3) + 'K';
      } else {
        return formatter.format(power);
      }
    },

    go(link) {
      window.location.href = link;
    }
  }
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 90vw;
}

.main-body {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}

.row {
  display: flex;
  flex-wrap: wrap;
}

.left-column, .right-column {
  width: 45vw;
  margin-bottom: 1rem;
}

.card {
  width: 100%;
  margin-bottom: 1rem;
}

.progress {
  height: 5px;
}

button {
  margin-top: 20px;
  padding: 10px 20px;
  font-size: 16px;
  background-color: #138500;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #8b4513;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.rounded-circle {
  border-radius: 50%!important;
}

.card-body {
  text-align: center;

}



</style>
