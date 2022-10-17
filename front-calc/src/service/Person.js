export default class CustomerService {


    getCustomersSmall() {
        return fetch('http://localhost:8081/api/persons').then(res => res.json())
            .then(d => d.data);
    }


}