<template xmlns="http://www.w3.org/1999/html">
  <div>
    <div class="grid">

      <div class="col-fixed " style="width:25%">
        <div style="margin-top:2em;margin-right: 1em">

          <Fieldset legend="VUE Credit calculator">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et
            dolore magna aliqua. Vitae et leo duis ut diam. Ultricies mi quis hendrerit dolor magna eget est lorem. Amet
            consectetur adipiscing elit ut. Nam libero justo laoreet sit amet. Pharetra massa massa ultricies mi quis
            hendrerit dolor magna. Est ultricies integer quis auctor elit sed vulputate. Consequat ac felis donec et.
            Tellus orci ac auctor augue mauris. Semper feugiat nibh sed pulvinar proin gravida hendrerit lectus a.
            Tincidunt arcu non sodales neque
          </Fieldset>
          <PanelMenu :model="items"/>
        </div>
      </div>

      <div class="col">
        <form @submit.prevent="handleSubmit(!v$.$invalid)">
          <div style="margin-top:6em"></div>

          <div className="grid p-fluid">

            <div className="field col-12 md:col-4">

              <label htmlFor="currency-us">Kwota</label>
              <InputNumber inputId="currency-us" v-model="v$.totalValueOfLoan.$model" mode="currency" currency="USD"
                           :class="{'p-invalid':v$.totalValueOfLoan.$invalid && submitted}"
                           locale="en-US"/>

              <span v-if="v$.totalValueOfLoan.$error && submitted">
                            <span id="currency-us" v-for="(error, index) of v$.totalValueOfLoan.$errors" :key="index">
                            <small class="p-error">{{ error.$message }}</small>
                            </span>
                            </span>
              <small v-else-if="(v$.totalValueOfLoan.$invalid && submitted) || v$.totalValueOfLoan.$pending.$response"
                     class="p-error">{{ v$.totalValueOfLoan.required.$message.replace('Value', 'Filed') }}</small>

            </div>
            <div className="field col-12 md:col-4">
              <label htmlFor="InputNumber">Lata</label>
              <InputNumber inputId="date" v-model="v$.howLongInYears.$model"
                           :class="{'p-invalid':v$.totalValueOfLoan.$invalid && submitted}"
                           locale="de-DE"/>
              <span v-if="v$.howLongInYears.$error && submitted">
                            <span id="date" v-for="(error, index) of v$.howLongInYears.$errors" :key="index">
                            <small class="p-error">{{ error.$message }}</small>
                            </span>
                            </span>
              <small v-else-if="(v$.howLongInYears.$invalid && submitted) || v$.howLongInYears.$pending.$response"
                     class="p-error">{{ v$.howLongInYears.required.$message.replace('Value', 'Filed') }}</small>

            </div>
            <div className="field col-12 md:col-4">
              <h5> Oprocentowanie: {{ creditInterest }}%</h5>
              <Slider v-model="creditInterest" :max="10" :min="3"/>
            </div>
          </div>
          <h5 v-if="result.result">Rata kredytu</h5>
          <transition-group name="p-message" tag="div">
            <InlineMessage v-if="result.result" severity="success"> {{ result.result }}</InlineMessage>
          </transition-group>

          <div class="move-to-left">

            <ConfirmPopup></ConfirmPopup>
            <ConfirmPopup group="demo">
              <template #message="slotProps">
                <div class="flex p-4">
                  <i :class="slotProps.message.icon" style="font-size: 1.5rem"></i>
                  <p class="pl-2">{{ slotProps.message.message }}</p>
                </div>
              </template>
            </ConfirmPopup>
            <Toast/>

            <div class="card remove-background">
              <Button type="submit" @click="confirm1($event)" icon="pi pi-check" label="Confirm" class="mr-2"></Button>
              <Button @click="confirm2($event)" icon="pi pi-times" label="Delete"
                      class="p-button-danger p-button-outlined"></Button>


            </div>

          </div>
        </form>

      </div>

    </div>

  </div>

</template>

<style>
.move-to-left {
  float: right;
}

</style>
<script>
import axios from 'axios';
import {minValue, numeric, required} from "@vuelidate/validators";
import {useVuelidate} from "@vuelidate/core";

export default {
  methods: {
    handleSubmit(isFormValid) {
      this.submitted = true;

      if (!isFormValid) {
        return;
      }

      this.toggleDialog();
    },
    toggleDialog() {
      this.showMessage = !this.showMessage;
    },

    confirm1(event) {
      this.$confirm.require({
        target: event.currentTarget,
        message: 'Are you sure you want to proceed?',
        icon: 'pi pi-exclamation-triangle',
        accept: () => {

          axios
              .post('http://localhost:8081/get-credit', {
                totalValueOfLoan: this.totalValueOfLoan,
                howLongInYears: this.howLongInYears,
                creditInterest: this.creditInterest
              })
              .then(response => {
                (this.result = response.data)
                this.notification(response.data)
                this.addMessages(response.data)
              })

        },

      });
    },

    confirm2(event) {
      this.$confirm.require({
        target: event.currentTarget,
        message: 'Do you want to delete this record?',
        icon: 'pi pi-info-circle',
        acceptClass: 'p-button-danger',
        accept: () => {
          this.creditInterest = 3
          this.howLongInYears = null
          this.totalValueOfLoan = null
          this.result = ''
          this.$toast.add({severity: 'info', summary: 'Confirmed', detail: 'Record deleted', life: 3000});
        },

      });
    },

    notification(result) {
      this.$toast.add({severity: result['message_type'], summary: 'Rejected', detail: result['message'], life: 3000});
    },
    addMessages(result) {
      this.messages = [
        {severity: 'success', content: result['result'], id: this.count++},
      ]
    },
  },
  setup: () => ({v$: useVuelidate()}),
  data() {
    return {
      totalValueOfLoan: null,
      howLongInYears: null,
      creditInterest: 3,
      result: '',
      value: 3,
      messages: [],
      submitted: false,
      showMessage: false

    }

  },
  validations() {
    return {
      totalValueOfLoan: {
        required,
        numeric,
        minValue: minValue(1)
      },
      howLongInYears: {
        required, numeric, minValue: minValue(1)
      },

    }
  },


}
</script>

