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
                  @click="editProduct(slotProps.data)"/>
          <Button icon="pi pi-trash" class="p-button-rounded p-button-warning"
                  @click="confirmDeleteProduct(slotProps.data)"/>
        </template>
      </Column>
      <!--   Buttons   -->
      <div style="margin-top:2em"></div>
      <Toolbar class="mb-4">
        <template #start>
          <Button label="New" icon="pi pi-plus" class="p-button-success mr-2" @click="openNew"/>
        </template>


      </Toolbar>
      <!-- End of buttons section -->
    </DataTable>


  </div>

  <Dialog v-model:visible="customers" :style="{width: '450px'}" header="Product Details" :modal="true" class="p-fluid">
    <div class="field">
      <label for="name">Name</label>
      <InputText id="name" v-model.trim="customers.name" required="true" autofocus
                 :class="{'p-invalid': submitted && !customers.name}"/>
      <small class="p-error" v-if="submitted && !customers.name">Name is required.</small>
    </div>
    <div class="field">
      <label for="description">surname</label>
      <Textarea id="description" v-model="customers.surname" required="true" rows="3" cols="20"/>
    </div>


    <!--    Start edit the product list-->
    <div class="field">
      <label class="mb-3">Category</label>
      <div class="formgrid grid">
        <div class="field-radiobutton col-6">
          <RadioButton id="category1" name="category" value="Accessories" v-model="product.category"/>
          <label for="category1">Accessories</label>
        </div>
        <div class="field-radiobutton col-6">
          <RadioButton id="category2" name="category" value="Clothing" v-model="product.category"/>
          <label for="category2">Clothing</label>
        </div>
        <div class="field-radiobutton col-6">
          <RadioButton id="category3" name="category" value="Electronics" v-model="product.category"/>
          <label for="category3">Electronics</label>
        </div>
        <div class="field-radiobutton col-6">
          <RadioButton id="category4" name="category" value="Fitness" v-model="product.category"/>
          <label for="category4">Fitness</label>
        </div>
      </div>
    </div>
    <template #footer>
      <Button label="Cancel" icon="pi pi-times" class="p-button-text" @click="hideDialog"/>
      <Button label="Save" icon="pi pi-check" class="p-button-text" @click="saveProduct"/>
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
      <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deleteProduct"/>
    </template>
  </Dialog>

  <Dialog v-model:visible="deleteProductsDialog" :style="{width: '450px'}" header="Confirm" :modal="true">
    <div class="confirmation-content">
      <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem"/>
      <span v-if="product">Are you sure you want to delete the selected products?</span>
    </div>
    <template #footer>
      <Button label="No" icon="pi pi-times" class="p-button-text" @click="deleteProductsDialog = false"/>
      <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deleteSelectedProducts"/>
    </template>
  </Dialog>
  <!--End remove the product-->

</template>

<script>
import CustomerService from '../service/Person';
import axios from "axios";

export default {
  data() {
    return {
      customers: null,
      products: null,
      productDialog: false,
      deleteProductDialog: false,
      deleteProductsDialog: false,
      product: {},
      selectedProducts: null,
      filters: {},
      submitted: false,
    }
  },
  customerService: null,
  created() {
    this.customerService = new CustomerService();
  },
  mounted() {

    return axios
        .get('http://localhost:8081/api/persons')
        .then(response => {
          (this.customers = response.data);
        })


  },
  methods: {
    openNew() {
      this.product = {};
      this.submitted = false;
      this.productDialog = true;
    },
    hideDialog() {
      this.productDialog = false;
      this.submitted = false;
    },
    saveProduct() {
      this.submitted = true;

      if (this.product.name.trim()) {
        if (this.product.id) {
          this.product.inventoryStatus = this.product.inventoryStatus.value ? this.product.inventoryStatus.value : this.product.inventoryStatus;
          this.products[this.findIndexById(this.product.id)] = this.product;
          this.$toast.add({severity: 'success', summary: 'Successful', detail: 'Product Updated', life: 3000});
        } else {
          this.product.id = this.createId();
          this.product.code = this.createId();
          this.product.image = 'product-placeholder.svg';
          this.product.inventoryStatus = this.product.inventoryStatus ? this.product.inventoryStatus.value : 'INSTOCK';
          this.products.push(this.product);
          this.$toast.add({severity: 'success', summary: 'Successful', detail: 'Product Created', life: 3000});
        }

        this.productDialog = false;
        this.product = {};
      }
    },
    editProduct(product) {
      this.product = {...product};
      this.productDialog = true;
    },
    confirmDeleteProduct(product) {
      this.product = product;
      this.deleteProductDialog = true;
    },
    deleteProduct() {
      this.products = this.products.filter(val => val.id !== this.product.id);
      this.deleteProductDialog = false;
      this.product = {};
      this.$toast.add({severity: 'success', summary: 'Successful', detail: 'Product Deleted', life: 3000});
    },

  }
}
</script>
