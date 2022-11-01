<template>

  <div class="card" style="height: calc(100vh - 143px)">

    <DataTable :value="customers" :scrollable="true" scrollHeight="flex">
      <Column field="name" header="Name"></Column>
      <Column field="surname" header="Country"></Column>
      <Column field="dateOfBirth" header="Representative"></Column>
      <Column field="status" header="Status"></Column>
      <Column :exportable="false" style="min-width:8rem">
        <template #body="slotProps">
          <Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2"
                  @click="editProductPOP(slotProps.data)"/>
          <Button icon="pi pi-trash" class="p-button-rounded p-button-warning"
                  @click="confirmDeleteProduct(slotProps.data)"/>
        </template>
      </Column>
      <!--   Buttons   -->
      <div style="margin-top:2em"></div>
      <Toolbar class="mb-4">
        <template #start>
          <Button label="New" icon="pi pi-plus" class="p-button-success mr-2" @click="showPOP"/>
        </template>


      </Toolbar>
      <!-- End of buttons section -->
    </DataTable>


  </div>

  <Dialog v-model:visible="productDialog" :style="{width: '450px'}" header="Product Details" :modal="true"
          class="p-fluid">
    <div class="field">
      <label for="name">Name</label>
      <InputText id="name" v-model="newCustomer.name" required="true" autofocus
                 :class="{'p-invalid': submitted && !newCustomer.name}"/>
      <small class="p-error" v-if="submitted && !newCustomer.name">Name is required.</small>
    </div>
    <div class="field">
      <label for="name">Name</label>
      <InputText id="name" v-model.trim="newCustomer.surname" required="true" autofocus
                 :class="{'p-invalid': submitted && !newCustomer.surname}"/>
      <small class="p-error" v-if="submitted && !newCustomer.surname">Name is required.</small>
    </div>
    <InputText v-show="false" id="name" v-model.trim="newCustomer.id" required="true" autofocus/>
    <div class="field">
      <label for="icon">Icon</label>
      <Calendar inputId="icon" v-model="newCustomer.dateOfBirth" :showIcon="true"/>
    </div>

    <template #footer>
      <Button label="Cancel" icon="pi pi-times" class="p-button-text" @click="hideDialog"/>
      <Button v-if="productDialogNew" label="Save" icon="pi pi-check" class="p-button-text" @click="saveProduct()"/>
      <Button v-if="productDialogEdit" label="Save" icon="pi pi-check" class="p-button-text" @click="editProduct()"/>
    </template>
  </Dialog>
  <!--    End edit the product list-->


  <!--Start remove the product-->

  <Dialog v-model:visible="deleteProductDialog" :style="{width: '450px'}" header="Confirm" :modal="true">
    <div class="confirmation-content">
      <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem"/>
      <span v-if="product">Are you sure you want to delete <b>{{ product.name }}</b>?</span>
    </div>
    <template #footer>
      <Button label="No" icon="pi pi-times" class="p-button-text" @click="deleteProductDialog = false"/>
      <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deleteProduct();"/>
    </template>
  </Dialog>

  <Dialog v-model:visible="deleteProductsDialog" :style="{width: '450px'}" header="Confirm" :modal="true">
    <div class="confirmation-content">
      <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem"/>
      <span v-if="product">Are you sure you want to delete the selected products?</span>
    </div>
    <template #footer>
      <Button label="No" icon="pi pi-times" class="p-button-text" @click="deleteProductsDialog = false"/>
      <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deleteSelectedProducts ;"/>
    </template>
  </Dialog>
  <!--End remove the product-->

</template>

<script>
import CustomerService from '../service/AuthService';
import axios from "axios";

export default {
  data() {
    return {
      customers: null,
      products: null,
      productDialogEdit: false,
      deleteProductDialog: false,
      deleteProductsDialog: false,
      deleteSelectedProducts: false,
      customer: {},
      selectedProducts: null,
      filters: {},
      submitted: false,
      addNewProduct: false,
      newCustomer: {},
      date3: null,
      productDialog: false,
      productDialogNew: false
    }

  },
  customerService: null,
  created() {
  },
  beforeMount() {

  },
  mounted() {
    axios
        .get('http://localhost:8081/api/persons/all')
        .then(response => {
          (this.customers = response.data)
        })
  },
  updated: function () {
    this.$nextTick(function () {
      this.openNew()
    })
  },
  methods: {

    openNew() {
      axios
          .get('http://localhost:8081/api/persons/all')
          .then(response => {
            (this.customers = response.data)
          })
    },
    hideDialog() {
      this.productDialogEdit = false;
      this.productDialogNew = false;
      this.submitted = false;
    },
    showPOP() {
      this.productDialog = true

      this.productDialogNew = true;

    },
    saveProduct() {

      console.log(this.newCustomer)

      this.axiosSendRequest("create", this.newCustomer)
    },
    editProduct() {
      if (typeof this.newCustomer.id === 'undefined') {
        this.newCustomer.id = this.product.id
        // your code here
      }
      if (typeof this.newCustomer.name === 'undefined') {
        this.newCustomer.name = this.product.name
        // your code here
      }
      if (typeof this.newCustomer.surname === 'undefined') {
        this.newCustomer.surname = this.product.surname
        // your code here
      }
      if (typeof this.newCustomer.dateOfBirth === 'undefined') {
        this.newCustomer.dateOfBirth = this.product.dateOfBirth
        // your code here
      }

      console.log(this.newCustomer, this.product)

      this.axiosSendRequest("edit", this.newCustomer)
    },
    editProductPOP(product) {
      this.productDialog = true
      this.product = {...product};
      this.productDialogEdit = true;
    },
    confirmDeleteProduct(product) {
      this.product = product;
      this.deleteProductDialog = true;
    },
    deleteProduct() {
      this.deleteProductDialog = true;
      console.log(this.product)
      this.axiosSendRequest("delete", this.product)
      this.deleteProductDialog = false
      this.$toast.add({severity: 'success', summary: 'Successful', detail: 'Product Deleted', life: 3000});

    },


    axiosSendRequest(typeOfAction, data) {
      console.log(typeOfAction, data)
      switch (true) {
        case typeOfAction === "delete":
          axios.post('http://localhost:8081/api/persons/delete', {
            id: data.id,
          }).catch(function (error) {
            console.log(error)
          })
          break;
        case typeOfAction === "create":
          axios.post('http://localhost:8081/api/persons/create', {
            name: data.name,
            surname: data.surname,
            dateOfBirth: data.dateOfBirth
          }).catch(function (error) {
            console.log(error)
          })
          break;
        case typeOfAction === "edit":
          axios.post('http://localhost:8081/api/persons/edit', {
            id: data.id,
            name: data.name,
            surname: data.surname,
            dateOfBirth: data.dateOfBirth
          }).catch(function (error) {
            console.log(error)
          })
          break;


      }

    }


  }
}
</script>
