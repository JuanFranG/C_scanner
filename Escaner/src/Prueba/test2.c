int suma(int a, int b) {
    return a + b;
}

float division(float x, float y) {
    if (y != 0.0) {
        return x / y;
    } else {
        printf("Error: División por cero\n");
        return 0.0;
    }
}

int main() {
    int resultado1 = suma(10, 20);
    printf("El resultado de la suma es %d\n", resultado1);

    float resultado2 = division(10.0, 5.0);
    printf("El resultado de la división es %f\n", resultado2);

    return 0;
}
