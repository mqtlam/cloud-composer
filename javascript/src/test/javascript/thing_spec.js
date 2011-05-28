describe("Thing", function() {
  it("greets people politely", function() {
    var thing = new Thing();
    expect(thing.greet("John")).toBe("Hello, John!");
  });
});
