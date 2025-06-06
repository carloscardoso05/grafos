public record Vertice(String label) {
    public Vertice {
        Assert.notBlank(label, "Label do vértice não pode ser nulo ou vazio");
    }
}
