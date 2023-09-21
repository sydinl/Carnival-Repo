module.exports =  () => {
  const rewrites = () => {
    return [
      {
        source: "/iflow/:path*",
        destination: "http://localhost:8080/iflow/:path*",
      },
    ];
  };
  return {
    rewrites,
  };
};
