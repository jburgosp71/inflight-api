
## ✈️ Inflight API - Airshop

API para la gestión de productos y pedidos a bordo de vuelos.

---

## 🔐 Seguridad

Todos los endpoints están protegidos mediante **Autenticación Básica HTTP (Basic Auth)**.  
Debes enviar el header:

```
Authorization: Basic <base64(username:password)>
```

---

## 📦 Endpoints de Productos

### 🔍 Listar todos los productos

**GET** `/api/products`

#### Parámetros opcionales
- `categoryId`: Filtrar productos por categoría.

#### Ejemplo de respuesta:
```json
[
  {
    "id": 1,
    "name": "Coca Cola",
    "price": 3.50,
    "stock": 25,
    "categoryId": 2
  }
]
```

---

### 🆕 Crear un producto

**POST** `/api/products`

#### Body:
```json
{
  "name": "Agua Mineral",
  "price": 2.00,
  "stock": 100,
  "categoryId": 1
}
```

#### Respuesta:
```json
{
  "id": 5,
  "name": "Agua Mineral",
  "price": 2.00,
  "stock": 100,
  "categoryId": 1
}
```

---

## 🗂️ Endpoints de Categorías

### 🔍 Listar categorías

**GET** `/api/categories`

#### Ejemplo de respuesta:
```json
[
  {
    "id": 1,
    "name": "Bebidas"
  },
  {
    "id": 2,
    "name": "Comidas"
  }
]
```

---

### 🆕 Crear una categoría

**POST** `/api/categories`

#### Body:
```json
{
  "name": "Snacks"
}
```

#### Respuesta:
```json
{
  "id": 3,
  "name": "Snacks"
}
```

---

## 🧾 Endpoints de Órdenes

### 🆕 Crear una orden

**POST** `/api/orders`

#### Body:
```json
{
  "seatLetter": "A",
  "seatNumber": 12
}
```

#### Respuesta:
```json
{
  "id": 1,
  "buyerEmail": null,
  "seatLetter": "A",
  "seatNumber": 12,
  "status": "PENDING",
  "totalPrice": 0.0
}
```

---

### 🔁 Modificar una orden

**PUT** `/api/orders/{orderId}`

#### Body:
```json
{
  "buyerEmail": "cliente@ejemplo.com",
  "items": [
    {
      "product": { "id": 1 },
      "quantity": 2
    }
  ]
}
```

#### Nota:
- Se verifica stock de productos.
- Se recalcula el total automáticamente.

---

### 🧾 Finalizar una orden (mock de pago)

**POST** `/api/orders/{orderId}/finalize`

#### Body:
```json
{
  "paymentDetails": {
    "status": "PAID",
    "cardToken": "tok_123456789",
    "gateway": "MockGateway"
  }
}
```

#### Respuesta:
```json
{
  "id": 1,
  "buyerEmail": "cliente@ejemplo.com",
  "seatLetter": "A",
  "seatNumber": 12,
  "status": "CONFIRMED",
  "totalPrice": 7.0
}
```

---

### 🔍 Obtener una orden

**GET** `/api/orders/{orderId}`

#### Respuesta:
```json
{
  "id": 1,
  "buyerEmail": "cliente@ejemplo.com",
  "seatLetter": "A",
  "seatNumber": 12,
  "status": "CONFIRMED",
  "totalPrice": 7.0
}
```

---

## 🛡️ Mock de Pasarela de Pago

- El pago se simula en el endpoint `/orders/{orderId}/finalize`.
- Puedes enviar un estado `PAID`, `FAILED` u `OFFLINE`.
- El comportamiento simula una pasarela real sin integración externa.

---

## 📌 Notas

- Todos los valores monetarios están en dólares.
- El stock de productos se valida al modificar una orden.
- Una orden sin stock suficiente no podrá ser actualizada.
- Una orden se puede crear inicialmente solo con el asiento.

---

## 🚀 Autenticación para pruebas

```
Usuario: admin
Contraseña: admin123
```
