<template>
  <div class="card" style="overflow-y: auto">
    <div class="icon-wrapper">
      <font-awesome-icon :icon="`fa-solid ${iconName}`" class="icon" />
    </div>
    <h4 class="description">{{ $t('daily_stat') }}</h4>
    <transition name="fade">
      <transition name="fade">
        <h2 class="stats">
          {{ $t('card.dailyWaste', { amount: "115g", cost: "40kr" }) }}
          <br>
          <br>
          {{ $t('card.todayWaste', { amount: food, cost: money }) }}
        </h2>
      </transition>
    </transition>
  </div>
</template>
<script>
export default {
  name: "StatCard",

  props: {
    food: {
      type: String
    },
    money: {
      type: String
    },
    iconName: {
      type: String,
      required: true,
    }
  }
}
</script>

<style scoped>

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.25s ease;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}

.description {
  text-align: center;
  font-size: 25px;
  font-weight: bold;
  margin-top: 5px;
  color: #3bd290;
}

.icon {
  font-weight: bold;
  font-size: 150%;
  top: 10%;
}

.icon-wrapper {
  align-self: start;
  color: white;
  background: linear-gradient(to right, #3bd290, #5cb8a5, #8fd6b2);
  width: 18%;
  height: 18%;
  box-shadow: 0 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 20px;
  text-align: center;
}

h4 {
  font-size: 18px;
  font-weight: bold;
  margin-top: 5px;
  color: #3b3b3b;
  text-align: start;
}

.card {
  display: flex;
  flex-direction: column;
  height: 100%;
  align-content: center;
  background-color: white;
  border-radius: 10px;
  cursor: pointer;
  transition: transform 0.2s ease-in-out;
  box-shadow: 0 4px 4px rgba(0, 0, 0, 0.25);
}

.card:hover {
  transform: translateY(-5px);
}

.stats {
  border-radius: 10px;
  height: 100%;
  max-width: 100%;
  display: flex;
  margin: 1rem 5% 2% .5rem;
  font-size: 1.5rem;
  text-align: start;
}
</style>
